package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.google.common.util.concurrent.Futures.FutureCombiner
import database._
import models.Front.FrontCalendrier
import models.choco.ChocoModule
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ModuleContrainteController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def save = Action.async{ request =>
		request.body.asJson.map { requ =>
			Json.fromJson[Seq[ChocoModule]](requ).map { req =>
				dbMongo.ModuleContrainteCollection.save(req).map{wr =>
					if (wr.size > 0) {
						Ok("Tout s'est bien passé")
					} else {
						InternalServerError("Error")
					}
				}
			}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
		}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
	}
}
