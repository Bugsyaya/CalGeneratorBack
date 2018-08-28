package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.ENIUniteFormation
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class UniteFormationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show: Action[AnyContent] = Action.async {
		db.UniteFormationCollection.all.map(result => Ok(toJson[Seq[ENIUniteFormation]](result)))
	}
	
	def byId(idUniteFormation: Int): Action[AnyContent] = Action.async {
		db.UniteFormationCollection.byId(idUniteFormation).map(result => Ok(toJson[Option[ENIUniteFormation]](result)))
	}
}
