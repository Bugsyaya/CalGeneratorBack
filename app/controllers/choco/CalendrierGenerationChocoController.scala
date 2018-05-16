package controllers.choco

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import models.choco._
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.Calendrier
import models.Front.FrontCalendrier
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json.toJson

class CalendrierGenerationChocoController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def generationCalendrier = Action.async { request =>
		request.body.asJson.map { requ =>
			println(s"requ : $requ")
			println(s"Json.fromJson[FrontCalendrier](requ) : ${Json.fromJson[FrontCalendrier](requ)}")
			Json.fromJson[FrontCalendrier](requ).map { req =>
				frontCalToChocoProbleme(req).map{chocoProbleme =>
					println(s"JSON PROBLEME :\n${toJson[Probleme](chocoProbleme).toString}")
					Http().singleRequest(
						HttpRequest(
							method = HttpMethods.POST,
							uri = "http://localhost:8000/solve",
							entity = HttpEntity(ContentTypes.`application/json`, toJson[Probleme](chocoProbleme).toString),
							headers = Nil
						)
					)
				}.flatMap(_.flatMap(res => chocoCalToCal(Json.parse(res.entity.toString).as[Seq[ChocoCalendrier]])))
			}.getOrElse(Future.successful(InternalServerError("Ce n'est pas un FrontCalendrier...")))
//			}.getOrElse(chocoCalToCal(bouchonCalendrier))
		}.getOrElse(Future.successful(NotFound("Il manque des parametres...")))
	}
	
	private def chocoCalToCal(chocoCalendriers: Seq[ChocoCalendrier]): Future[Result] = {
		Future.sequence(
			chocoCalendriers.map{chocoCalendrier =>
				for {
					coursFutur <- Future.sequence(
							chocoCalendrier.cours.map { chocoCours =>
								db.CoursCollection.byId(chocoCours.idCours)
									.filter(_.isDefined)
									.map(_.get)
						}
					)
					result = Calendrier(coursFutur, chocoCalendrier.contraintesResolus, chocoCalendrier.contrainteNonResolu)
				} yield result
			}
		).map(result => Ok(toJson[Seq[Calendrier]](result)))
	}
	
	private def frontCalToChocoProbleme(frontCalendrier: FrontCalendrier): Future[Probleme] = {
		val debut = frontCalendrier.periode.debut.split(" ").headOption.getOrElse(frontCalendrier.periode.debut)
		val fin = frontCalendrier.periode.fin.split(" ").headOption.getOrElse(frontCalendrier.periode.fin)
		
		db.ModuleCollection.byDateAndFormation(debut, fin, frontCalendrier.codeFormation).flatMap { idsModule =>
			Future.sequence(idsModule.map { idModule =>
				db.ModuleCollection.byId(idModule).filter(_.isDefined).flatMap { module =>
					db.CoursCollection.byDateAndModule(debut, fin, idModule).flatMap { idsCours =>
						Future.sequence(idsCours.map { idCours =>
							db.CoursCollection.byId(idCours).filter(_.isDefined).map(_.get)
						}).map { cs =>
							ChocoModulesFormation(
								module.get.idModule,
								Nil,
								cs.map { c =>
									ChocoCours(
										ChocoPeriode(
											c.debut,
											c.fin
										),
										c.idCours,
										c.idModule,
										c.codeLieu,
										c.dureeReelleEnHeures
									)
								},
								module.get.dureeEnSemaines,
								module.get.dureeEnHeures
							)
						}
					}
				}
			})
		}.map {chocoModulesFormation =>
			Probleme(
				ChocoPeriode(
					debut,
					fin
				),
				chocoModulesFormation,
				Nil
			)
		}
	}
	
	def bouchonCalendrier: Seq[ChocoCalendrier] = {
		val cours = Seq(
			ChocoCours(
				ChocoPeriode(
					"2018-03-26",
					"2018-03-30"
				),
				"9ac9f5b9-be0f-418d-ac3c-00ebb8582246",
				20,
				11,
				35
			),
			ChocoCours(
				ChocoPeriode(
					"2018-08-20",
					"2018-08-24"
				),
				"cdf20f3c-94d4-4069-a640-00e3100fdae1",
				541,
				11,
				35
			)
		)
		val cours2 = Seq(
			ChocoCours(
				ChocoPeriode(
					"2018-05-14",
					"2018-05-18"
				),
				"86335786-9ab8-4e22-a3c1-013b753c110f",
				822,
				5,
				35
			),
			ChocoCours(
				ChocoPeriode(
					"2018-07-16",
					"2018-07-27"
				),
				"d18784ed-6e39-44b9-8800-012f900e76d5",
				517,
				2,
				70
			)
		)
		
		val contraintesResolus = Seq(
			ChocoContrainte(
				dureeMaxFormation = Option(300)
			),
			ChocoContrainte(
				nbHeureAnnuel = Option(150)
			)
		)
		
		val contrainteNonResolu = Seq(
			ChocoContrainte(
				maxSemaineFormation = Option(1)
			),
			ChocoContrainte(
				maxStagiaireEntrepriseEnFormation = Option(1)
			)
		)
		
		Seq(
			ChocoCalendrier(
				cours,
				contraintesResolus,
				contrainteNonResolu
			),
			ChocoCalendrier(
				cours2,
				contrainteNonResolu,
				contraintesResolus
			)
		)
	}
}
