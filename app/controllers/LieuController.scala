package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.ENILieu
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class LieuController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show: Action[AnyContent] = Action.async {
		db.LieuCollection.all.map(result => Ok(toJson[Seq[ENILieu]](result)))
	}
	
	def byCodeLieu(codeLieu: Int): Action[AnyContent] = Action.async {
		db.LieuCollection.byCodeLieu(codeLieu).map(result => Ok(toJson[Option[ENILieu]](result)))
	}
}