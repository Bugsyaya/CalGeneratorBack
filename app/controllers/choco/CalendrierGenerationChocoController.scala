package controllers.choco

import java.util.UUID.randomUUID
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.stream.ActorMaterializer
import database._
import helper.API
import models.Calendrier
import models.ENI.{ENICours, ENIModule}
import models.Front.{FrontModulePrerequis, FrontModulePrerequisPlanning, FrontProblem}
import models.choco.Constraint.Sortie.ChocoCalendrier
import models.choco._
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

class CalendrierGenerationChocoController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))

	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def generationCalendrier = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[FrontProblem](requ).map { req =>
				
				for {
					chocoProblem <- frontProblemToChocoProblem(req)
				
					_ = println(s"chocoProblem : $chocoProblem")
					request <- Http().singleRequest(
									HttpRequest(
										method = HttpMethods.POST,
										uri = "http://localhost:8000/solve",
										entity = HttpEntity(ContentTypes.`application/json`, toJson[ChocoProbleme](chocoProblem).toString),
										headers = Nil
									)
								)
				
					chocoCalendriers <- request.entity
						.toStrict(300.millis)
						.map(_.data.utf8String)
						.map{chocoCal =>
							Json.parse(chocoCal).as[Seq[ChocoCalendrier]]
						}
				
					calendriers <- chocoCalToCal(chocoCalendriers)
				} yield {
					Ok(toJson[Seq[Calendrier]](calendriers))
				}
			}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
		}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
	}
	
	def frontProblemToChocoProblem(frontProblem: FrontProblem): Future[ChocoProbleme] = {
		for {
			allModule <- API.moduleByFormation(frontProblem.codeFormation).map(_.map(_.toInt))
			
			chocoConstraints <- if (frontProblem.idConstraint.isDefined) dbMongo.ChocoConstraintCollection.byId(frontProblem.idConstraint.get)
			else Future.successful(None)
			
			moduleOfTraining: Seq[ChocoModule] <- frontProblem.idModulePrerequisPlanning.map { frontProblemIdModulePrerequisPlanning =>
				for {
					modulePrerequisPlanning <- findModulePrerequisPlanning(frontProblemIdModulePrerequisPlanning)
				
					modulePrerequis <- if (modulePrerequisPlanning.isDefined) Future.sequence(modulePrerequisPlanning.get.idModulePrerequis.map(s => findModulePrerequis(s).filter(_.isDefined).map (_.get)))
					else Future.successful(Seq.empty)
				
					moduleWithPrerequis = allModule.filter(module => modulePrerequis.map(_.idModule).contains(module)).flatMap{mod => modulePrerequis.filter(m => m.idModule == mod)}
					_ = println(s"moduleWithPrerequis : $moduleWithPrerequis")
					
					moduleWithoutPrerequis = allModule.filterNot(module => modulePrerequis.map(_.idModule).contains(module))
					_ = println(s"moduleWithoutPrerequis : $moduleWithoutPrerequis")
					
				r1 <- Future.sequence(moduleWithPrerequis.map{mod =>
					createChocoModule(mod, frontProblem.periodOfTraining).filter(_.isDefined).map(_.get)
				})
				
				r2 <- Future.sequence(moduleWithoutPrerequis.map{mod =>
					for {
						listClasses <- db.CoursCollection.byDateAndModule(frontProblem.periodOfTraining.start, frontProblem.periodOfTraining.end, mod).flatMap { cours =>
								Future.sequence(cours.map { c =>
									db.CoursCollection.byId(c).filter(_.isDefined).map{rrr =>
										createChocoClasses(rrr.get)
									}
								})
							}
						
	                    chocoModules <- db.ModuleCollection.byId(mod).filter(_.isDefined).map { r =>
	                        ChocoModule(
	                            idModule = r.get.idModule,
	                            nbWeekOfModule = r.get.dureeEnSemaines,
	                            nbHourOfModule = r.get.dureeEnHeures,
	                            listIdModulePrerequisite = Seq.empty,
	                            listIdModuleOptional = Seq.empty,
	                            listClasses = listClasses
	                        )
	                    }
						
					} yield chocoModules
				})
				} yield {
					r1 ++ r2
				}
			}.getOrElse(Future.successful(Seq.empty))
			
			chocoProbleme = ChocoProbleme(
				periodOfTraining = frontProblem.periodOfTraining,
				numberOfCalendarToFound = frontProblem.numberOfCalendarToFound,
				constraints = chocoConstraints,
				moduleOfTraining = moduleOfTraining
			)
		} yield {
			chocoProbleme
		}
	}
	
	private def findModulePrerequisPlanning(idModulePrerequisPlanning: String): Future[Option[FrontModulePrerequisPlanning]] = dbMongo.ModulePrerequisPlanningCollection.byId(idModulePrerequisPlanning)
	
	private def findModulePrerequis(idModulePrerequis: String): Future[Option[FrontModulePrerequis]] = dbMongo.ModulePrerequisCollection.byId(idModulePrerequis)
	
	
	private def createChocoModule(frontModulePrerequis: FrontModulePrerequis, periodOfTraining: ChocoPeriod): Future[Option[ChocoModule]] = {
		for {
			infoModule: Option[ENIModule] <- db.ModuleCollection.byId(frontModulePrerequis.idModule)
		
			listClasses <- infoModule.map{infoModule =>
				findClassesByModule(infoModule.idModule, periodOfTraining)
			}.getOrElse(Future.successful(Seq.empty))
			
			chocomodule = infoModule.map(infoModule =>
				ChocoModule(
					idModule = frontModulePrerequis.idModule,
					nbWeekOfModule = infoModule.dureeEnSemaines,
					nbHourOfModule = infoModule.dureeEnHeures,
					listIdModulePrerequisite = frontModulePrerequis.idModuleObligatoire,
					listIdModuleOptional = frontModulePrerequis.idModuleOpionnel,
					listClasses = listClasses
				)
			)
		} yield {
			chocomodule
		}
	}
	
	private def findClassesByModule(moduleId: Int, periodOfTraining: ChocoPeriod): Future[Seq[ChocoClasses]] = {
		db.CoursCollection.byModule(moduleId)
			.flatMap { chocoClassesId =>
				Future.sequence(chocoClassesId.map { chocoClasseId =>
					db.CoursCollection.byId(chocoClasseId).map { enicoursOpt: Option[ENICours] =>
						enicoursOpt
							.filter(cours => cours.debut >= periodOfTraining.start && cours.fin <= periodOfTraining.end)
							.map { cours =>
								createChocoClasses(cours)
							}
					}
				}).map(_.filter(_.isDefined).map(_.get))
			}
	}
	
	private def createChocoClasses(cours: ENICours) = {
		ChocoClasses(
			idClasses = cours.idCours,
			period = ChocoPeriod(
				cours.debut.split(" ").head,
				cours.fin.split(" ").head
			),
			realDuration = cours.dureeReelleEnHeures,
			idPlace = cours.codeLieu
		)
	}
	
	private def chocoCalToCal(chocoCalendriers: Seq[ChocoCalendrier]): Future[Seq[Calendrier]] = {
	 chocoCaltoCal(chocoCalendriers).flatMap{cal =>
			Future.sequence(cal.map{c =>
				dbMongo.CalendrierCollection.save(c).map { wr => c }
			})
		}
	}
	
	private def chocoCaltoCal(chocoCalendriers: Seq[ChocoCalendrier]): Future[Seq[Calendrier]] = {
		Future.sequence(chocoCalendriers.map{chocoCalendrier =>
			Future.sequence(chocoCalendrier.cours.map{c =>
				for{
				    cou <- db.CoursCollection.byId(c.idClasses)
				} yield cou
			}).map{cc =>
				if (cc.filter(_.isDefined).size > 0) Calendrier(randomUUID().toString, chocoCalendrier.constraints, cc.filter(_.isDefined).map(_.get))
				else Calendrier(randomUUID().toString, chocoCalendrier.constraints, Seq.empty)
			}
		})
	}
}
