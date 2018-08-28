package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import helper.API
import models.ENI.{ENIFormation, ENIModule}
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class FormationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show: Action[AnyContent] = Action.async {
		db.FormationCollection.all.map(result => Ok(toJson[Seq[ENIFormation]](result)))
	}
	
	def byCodeFormation(codeFormation: String): Action[AnyContent] = Action.async {
		db.FormationCollection.byCodeFormation(codeFormation).map(result => Ok(toJson[Option[ENIFormation]](result)))
	}
	
	def moduleByCodeFormation(codeFormation: String): Action[AnyContent] = Action.async {
		API.moduleByFormation(codeFormation).flatMap { modules =>
			Future.sequence(modules.map { module =>
				db.ModuleCollection.byId(module.toInt)
					.filter(_.isDefined)
					.map(_.get)
			}).map(m => Ok(toJson[Seq[ENIModule]](m)))
		}
	}
}