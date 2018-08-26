package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.google.common.util.concurrent.Futures.FutureCombiner
import database._
import models.choco.ChocoModule
import models.database.ConstraintModule
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ModuleContrainteController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def save = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[Seq[ConstraintModule]](requ).map { constraintModules =>
				dbMongo.ConstraintModuleCollection.save(constraintModules).map{wr =>
					Ok(Json.toJson[Seq[ConstraintModule]](constraintModules))
				}
			}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
		}.getOrElse(Future.successful(InternalServerError("Il manque des parametres")))
	}
}
