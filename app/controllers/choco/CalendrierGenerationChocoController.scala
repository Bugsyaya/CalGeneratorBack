package controllers.choco

import java.util.UUID
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import database._
import helper.API
import models.Calendrier
import models.ENI.{ENICours, ENICoursCustom, ENIModule}
import models.Front.{FrontModulePrerequis, FrontModulePrerequisPlanning, FrontProblem}
import models.choco.Constraint.Sortie.ChocoCalendrier
import models.choco._
import models.database.Constraint
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class CalendrierGenerationChocoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def generationCalendrier: Action[AnyContent] = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[FrontProblem](requ).map { req =>
				for {
					chocoProblem <- frontProblemToChocoProblem(req)
					_ = println(s"toJson[ChocoProbleme](chocoProblem).toString : ${toJson[ChocoProbleme](chocoProblem).toString}")
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
						.map { chocoCal =>
							Json.parse(chocoCal).as[Seq[ChocoCalendrier]]
						}
					_ = println(s"chocoCalendriers : ${toJson[Seq[ChocoCalendrier]](chocoCalendriers)}")
				
					calendriers <- chocoCaltoCal(chocoCalendriers.map(_.copy(periodOfTraining = Some(req.periodOfTraining))), req.idConstraint, req.idModulePrerequisPlanning)
					_ = println(s"calendriers : ${toJson[Seq[Calendrier]](calendriers)}")
				} yield {
					Ok(toJson[Seq[Calendrier]](calendriers.map(_.copy(codeFormation = Some(req.codeFormation)))))
				}
			}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
		}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
	}
	
	def saveCalendrier: Action[AnyContent] = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[Calendrier](requ).map { req =>
				dbMongo.CalendrierCollection.save(req.copy(status = "created")).map { wr =>
					if (wr.n > 0) Ok(toJson("save"))
					else InternalServerError(toJson("error"))
				}
			}.getOrElse(Future.successful(BadRequest(toJson("error calendar"))))
		}.getOrElse(Future.successful(InternalServerError(toJson("error"))))
	}
	
	def checkCalendar(idCalendrier: String): Action[AnyContent] = Action.async {
		dbMongo.CalendrierCollection.byId(idCalendrier).flatMap { optCalendrier =>
			for {
				chocoModule <- optCalendrier.map { calendrier =>
					
					println(s"calendrier : ${toJson[Calendrier](calendrier)}")
					
					Future.sequence(
						calendrier.cours.map { cour =>
							db.ModuleCollection.byId(cour.idModule).flatMap { moduleOpt =>
								
								for {
									(prerequisOptionnel: Seq[Int], prerequisObligatoires: Seq[Int]) <- calendrier.idModulePrerequisPlanning.map { idModulerPrerequisPlanning =>
										dbMongo.ModulePrerequisPlanningCollection.byId(idModulerPrerequisPlanning).flatMap { frontModulePrerequisPlanningOpt =>
											frontModulePrerequisPlanningOpt.map { frontModulePrerequisPlanning =>
												Future.sequence(frontModulePrerequisPlanning.idModulePrerequis.map { idModulePrerequis =>
													dbMongo.ModulePrerequisCollection.byId(idModulePrerequis).map { frontModulePrerequisOpt =>
														frontModulePrerequisOpt.filter { frontModulePrerequis =>
															frontModulePrerequis.idModule == cour.idModule
														}.map { frontModulePrerequis =>
															(frontModulePrerequis.idModuleOpionnel, frontModulePrerequis.idModuleObligatoire)
														}.getOrElse((Seq.empty, Seq.empty))
													}
												}).map { i =>
													(i.flatMap(_._1), i.flatMap(_._2))
												}
											}.getOrElse(Future.successful((Seq.empty, Seq.empty)))
										}
									}.getOrElse(Future.successful((Seq.empty, Seq.empty)))
									
									chocoModOpt = moduleOpt.map { module =>
										ChocoModule(
											idModule = cour.idModule,
											nbWeekOfModule = module.dureeEnSemaines,
											nbHourOfModule = module.dureeEnHeures,
											listIdModulePrerequisite = prerequisObligatoires,
											listIdModuleOptional = prerequisOptionnel,
											listClasses = Seq(
												ChocoClasses(
													idClasses = cour.idCours,
													period = ChocoPeriod(
														start = cour.debut.split(" ").head,
														end = cour.fin.split(" ").head
													),
													realDuration = cour.dureeReelleEnHeures,
													idPlace = cour.codeLieu
												)
											)
										)
									}
								} yield chocoModOpt
							}
						}).map(jj => jj.filter(_.isDefined).map(_.get))
				}.getOrElse(Future.successful(Seq.empty))
				
				
				_ = println(s"optCalendrier : $optCalendrier")
				
				chocoCalendriers <- optCalendrier.map { calendrier =>
					Http().singleRequest(
						HttpRequest(
							method = HttpMethods.POST,
							uri = "http://localhost:8000/verify",
							entity = HttpEntity(ContentTypes.`application/json`, toJson[ChocoVerify](ChocoVerify(
								periodOfTraining = calendrier.periodOfTraining,
								constraints = calendrier.constraint,
								moduleOfTraining = chocoModule
							)).toString),
							headers = Nil
						)
					).flatMap { httpRequest: HttpResponse =>
						val u = httpRequest.entity
							.toStrict(300.millis)
							.map(_.data.utf8String)
							.map { chocoCal =>
								Json.parse(chocoCal).as[ChocoCalendrier]
							}
						u.map(c => Seq(c.copy(periodOfTraining = calendrier.periodOfTraining)))
					}
				}.getOrElse(Future.successful(Seq.empty))
				
				calendriers <- chocoCaltoCal(chocoCalendriers)

				cal: Calendrier = Calendrier(
					idCalendrier = optCalendrier.map(_.idCalendrier).getOrElse(UUID.randomUUID().toString),
					status = "checked",
					periodOfTraining = optCalendrier.flatMap(_.periodOfTraining),
					constraint = optCalendrier.flatMap(_.constraint),
					cours = calendriers.head.cours,
					idModulePrerequisPlanning = optCalendrier.flatMap(_.idModulePrerequisPlanning),
					titre = optCalendrier.flatMap(_.titre),
					description = optCalendrier.flatMap(_.description),
					codeFormation = optCalendrier.flatMap(_.codeFormation)
				)
			
				calendrierF <- dbMongo.CalendrierCollection.update(cal).map(_ => cal)
			} yield {
				Ok(toJson[Calendrier](calendrierF))
			}
		}
	}
	
	def frontProblemToChocoProblem(frontProblem: FrontProblem): Future[ChocoProbleme] = {
		for {
			allModule <- API.moduleByFormation(frontProblem.codeFormation).map(_.map(_.toInt))
			
			chocoConstraints <- if (frontProblem.idConstraint.isDefined) dbMongo.ChocoConstraintCollection.byId(frontProblem.idConstraint.get)
			else Future.successful(None)
			
			moduleOfTraining: Seq[ChocoModule] <- frontProblem.idModulePrerequisPlanning.map { frontProblemIdModulePrerequisPlanning =>
				for {
					modulePrerequisPlanning <- findModulePrerequisPlanning(frontProblemIdModulePrerequisPlanning)
					
					modulePrerequis <- if (modulePrerequisPlanning.isDefined) Future.sequence(modulePrerequisPlanning.get.idModulePrerequis.map(s => findModulePrerequis(s).filter(_.isDefined).map(_.get)))
					else Future.successful(Seq.empty)
					
					moduleWithPrerequis = allModule.filter(module => modulePrerequis.map(_.idModule).contains(module)).flatMap { mod => modulePrerequis.filter(m => m.idModule == mod) }
					
					moduleWithoutPrerequis = allModule.filterNot(module => modulePrerequis.map(_.idModule).contains(module))
					
					r1 <- Future.sequence(moduleWithPrerequis.map { mod =>
						createChocoModule(mod, frontProblem.periodOfTraining).filter(_.isDefined).map(_.get)
					})
					
					r2 <- Future.sequence(moduleWithoutPrerequis.map { mod =>
						for {
							listClasses <- db.CoursCollection.byDateAndModule(frontProblem.periodOfTraining.start, frontProblem.periodOfTraining.end, mod).flatMap { cours =>
								Future.sequence(cours.map { c =>
									db.CoursCollection.byId(c).filter(_.isDefined).map { rrr =>
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
			
			listClasses <- infoModule.map { infoModule =>
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
	
	private def chocoCaltoCal(chocoCalendriers: Seq[ChocoCalendrier], idConstraint: Option[String] = None, idModulePrerequisPlanning: Option[String] = None): Future[Seq[Calendrier]] = {
		val conF = if (idConstraint.isDefined) dbMongo.ConstraintCollection.byId(idConstraint.get)
		else Future.successful(None)
		
		Future.sequence(chocoCalendriers.map { chocoCalendrier =>
			Future.sequence(chocoCalendrier.cours.map { c =>
				for {
					cou <- db.CoursCollection.byId(c.idClasses)
				} yield cou
			}).flatMap { cc =>
				val eniCoursCustom = cc.map(_.map(cccc =>
					ENICoursCustom(
						cccc.debut,
						cccc.fin,
						cccc.dureeReelleEnHeures,
						cccc.codePromotion,
						cccc.idCours,
						cccc.idModule,
						cccc.libelleCours,
						cccc.dureePrevueEnHeures,
						cccc.dateAdefinir,
						cccc.codeLieu,
						chocoCalendrier.cours.filter(_.idClasses == cccc.idCours).flatMap(_.constraints)
					)
				)
				)
				
				conF.map(c =>
					Calendrier(
						UUID.randomUUID().toString,
						"created",
						chocoCalendrier.periodOfTraining,
						c,
						eniCoursCustom.filter(_.isDefined).map(_.get),
						idModulePrerequisPlanning
					)
				)
			}
		})
	}
}
