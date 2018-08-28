package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.ENISalle
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class SalleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show: Action[AnyContent] = Action.async {
		db.SalleCollection.all.map(result => Ok(toJson[Seq[ENISalle]](result)))
	}
	
	def byCodeSalle(codeSalle: String): Action[AnyContent] = Action.async {
		db.SalleCollection.byCodeSalle(codeSalle).map(result => Ok(toJson[Option[ENISalle]](result)))
	}
}