package controllers

import java.util.UUID
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import models.ModuleFormation
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ModuleFormationConstraintController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def create = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[ModuleFormation](requ).map { req =>
				db.FormationCollection.moduleByCodeFormation(req.codeFormation).flatMap{idModules =>
					val moduleFormation = ModuleFormation(
						Some(UUID.randomUUID().toString),
						req.codeFormation,
						req.titre,
						req.description,
						Some(idModules.map(_.toInt))
					)
					dbMongo.ModuleFormationCollection.save(moduleFormation).map{wr =>
						if (wr.n > 0) Ok(Json.toJson[ModuleFormation](moduleFormation))
						else InternalServerError("Une erreur est survenue")
					}
				}
				
			}.getOrElse(Future.successful(InternalServerError("Ce n'est pas un ModuleFormation...")))
		}.getOrElse(Future.successful(NotFound("Il manque des parametres...")))
	}
	
}
