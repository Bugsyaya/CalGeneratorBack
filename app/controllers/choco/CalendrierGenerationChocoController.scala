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
			Json.fromJson[Probleme](requ).map { req =>
				Http().singleRequest(
					HttpRequest(
						method = HttpMethods.GET,
						uri = "",
						entity = HttpEntity(ContentTypes.`application/json`, toJson[Probleme](Json.fromJson[Probleme](requ).get).toString),
						headers = Nil
					)
				).flatMap{res =>
					chocoCalToCal(Json.parse(res.entity.toString).as[Seq[ChocoCalendrier]])
				}
//			}.getOrElse(Future.successful(InternalServerError("Ce n'est pas un probleme...")))
			}.getOrElse(chocoCalToCal(bouchonCalendrier))
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
