package controllers

import java.util.UUID
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import models.choco.ChocoConstraint
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ContrainteController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))

	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()

	def create = Action.async { request =>
		request.body.asJson.map{response =>
			println(response)
			println(Json.fromJson[ChocoConstraint](response))
			Json.fromJson[ChocoConstraint](response).map{chocoContrainte =>
				val cc = chocoContrainte.copy(idConstraint = Some(UUID.randomUUID().toString))
				dbMongo.ChocoConstraintCollection.create(cc).map{wr =>
					if(wr.n > 0) Ok("C'est enregistré")
					else InternalServerError("Il y a eu une erreur")
				}
			}.getOrElse(Future.successful(BadRequest("Il manque des param")))
		}.getOrElse(Future.successful(BadRequest("Y a un pb")))
	}
}
