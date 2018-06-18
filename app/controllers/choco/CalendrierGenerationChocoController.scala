package controllers.choco

import java.util.UUID.randomUUID
import javax.inject.Inject

import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import models.choco._
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import akka.stream.ActorMaterializer
import database._
import models.{Calendrier, ModuleFormation}
import models.Front.FrontCalendrier
import play.api.libs.json.Json

import scala.concurrent.{Await, ExecutionContext, Future}
import play.api.libs.json.Json.toJson

class CalendrierGenerationChocoController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def generationCalendrier = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[FrontCalendrier](requ).map { req =>
				
				val calendrierF: Future[Seq[Calendrier]] = frontCalToChocoProbleme(req).flatMap{chocoProbleme =>
					requestProbleme(chocoProbleme)
						.flatMap{res =>
						res.entity
							.toStrict(300.millis)
							.map( _.data.utf8String)
							.map(Json.parse(_).as[Seq[ChocoCalendrier]])
							.flatMap(r => chocoCalToCal(r, req.codeFormation))
					}
				}
				
				calendrierF.map(calendrier => Ok(toJson[Seq[Calendrier]](calendrier)))
			}.getOrElse(Future.successful(InternalServerError("Ce n'est pas un FrontCalendrier...")))
		}.getOrElse(Future.successful(NotFound("Il manque des parametres...")))
	}
	
	private def chocoCalToCal(chocoCalendriers: Seq[ChocoCalendrier], codeFormation: String): Future[Seq[Calendrier]] = {
		Future.sequence(chocoCalendriers.map{c =>
			chocoCaltoCal(c, codeFormation).flatMap{cal =>
				dbMongo.CalendrierCollection.save(cal).map { wr =>
					if (wr.n > 0) println("C'est ok")
					else println("C'est pas bon")
				}.map(_ => cal)
			}
		})
	}
	
	private def requestProbleme(chocoProbleme: ChocoProbleme) = {
		Http().singleRequest(
			HttpRequest(
				method = HttpMethods.POST,
				uri = "http://localhost:8000/solve",
				entity = HttpEntity(ContentTypes.`application/json`, toJson[ChocoProbleme](chocoProbleme).toString),
				headers = Nil
			)
		)
	}
	
	private def chocoCaltoCal(chocoCalendrier: ChocoCalendrier, codeFormation: String): Future[Calendrier] = {
		for {
			coursFutur <- Future.sequence(
				chocoCalendrier.cours.map { chocoCours =>
					db.CoursCollection.byId(chocoCours)
						.filter(_.isDefined)
						.map{cou =>
							cou.get.copy(
								debut = cou.get.debut.split(" ").headOption.getOrElse(cou.get.debut),
								fin = cou.get.fin.split(" ").headOption.getOrElse(cou.get.fin),
							)
						}
				}
			)
			result = Calendrier(randomUUID().toString, codeFormation, coursFutur, chocoCalendrier.contraintesResolus, chocoCalendrier.contrainteNonResolu)
			_ = println(s"resuuuult : $result")
		} yield result
	}
	
	private def frontCalToChocoProbleme(frontCalendrier: FrontCalendrier): Future[ChocoProbleme] = {
		val debut = frontCalendrier.periodOfTraining.start.split(" ").headOption.getOrElse(frontCalendrier.periodOfTraining.start)
		val fin = frontCalendrier.periodOfTraining.end.split(" ").headOption.getOrElse(frontCalendrier.periodOfTraining.end)
		
		db.ModuleCollection.byDateAndFormation(debut, fin, frontCalendrier.codeFormation).flatMap { idsModule =>
			Future.sequence(idsModule.map { idModule =>
				db.ModuleCollection.byId(idModule)
					.filter(_.isDefined)
					.flatMap { module =>
						db.CoursCollection.byDateAndModule(debut, fin, idModule).flatMap { idsCours =>
							Future.sequence(idsCours.map { idCours =>
							db.CoursCollection.byId(idCours).filter(_.isDefined).map(_.get)
						}).flatMap{ cs =>
							
							val moduleFormationF: Future[ModuleFormation] = dbMongo.ProblemCollection.byId(frontCalendrier.idProblem).flatMap{optProbleme =>
								optProbleme.map{probleme =>
									probleme.idModuleFormation.map{idFormation =>
										dbMongo.ModuleFormationCollection.byId(idFormation).map(mod => mod.get)
									}.get
								}.get
							}
							
							moduleFormationF.flatMap{moduleFormation =>
								Future.sequence(moduleFormation.chocoModule.map{chocoM =>
									Future.successful(dbMongo.ModuleContrainteCollection.byId(chocoM).map(_.get))
								}.map(g => g.flatMap(cm => cm)))
							}
//
//							ChocoModule(
//								module.get.idModule,
//								module.get.dureeEnSemaines,
//								module.get.dureeEnHeures,
//
//							)
//							ChocoModule(
//								module.get.idModule,
//								Nil,
//								cs.map { c =>
//									ChocoCours(
//										ChocoPeriod(
//											c.debut.split(" ").headOption.getOrElse(c.debut),
//											c.fin.split(" ").headOption.getOrElse(c.debut)
//										),
//										c.idCours,
//										c.idModule,
//										c.codeLieu,
//										c.dureeReelleEnHeures
//									)
//								},
//								module.get.dureeEnSemaines,
//								module.get.dureeEnHeures
//							)
						}
					}
				}
			})
		}.map {chocoModulesFormation =>
			ChocoProbleme(
				periodOfTrainning = Some(ChocoPeriod(debut, fin)),
				frontCalendrier.numberOfCalendarToFound.getOrElse(5),
				None,
				chocoModulesFormation.flatMap(p => p)
			)
		}
	}
}
