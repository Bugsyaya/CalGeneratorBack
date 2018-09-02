package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import models.Front.FrontModulePrerequisPlanning
import play.api.libs.json.Json
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class FrontModulePrerequisPlanningController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def create: Action[AnyContent] = Action.async { request =>
		request.body.asJson.map { requ =>
			Json.fromJson[FrontModulePrerequisPlanning](requ).map { req =>
				dbMongo.ModulePrerequisPlanningCollection.create(req).map { wr =>
					if (wr.n > 0) Ok("Module prerequis planning enregistré")
					else InternalServerError("Une erreur est survenue")
				}
			}.getOrElse(Future.successful(InternalServerError("Ce n'est pas un ModuleFormation...")))
		}.getOrElse(Future.successful(NotFound("Il manque des parametres...")))
	}
	
	def byId(idFrontModulePrerequisPlanning: String): Action[AnyContent] = Action.async {
		dbMongo.ModulePrerequisPlanningCollection.byId(idFrontModulePrerequisPlanning).map(result => Ok(toJson[Option[FrontModulePrerequisPlanning]](result)))
	}
	
	def show: Action[AnyContent] = Action.async {
		dbMongo.ModulePrerequisPlanningCollection.all.map(result => Ok(toJson[Seq[FrontModulePrerequisPlanning]](result)))
	}
}
