package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.Lieu
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class LieuController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.LieuCollection.all.map(result => Ok(toJson[Seq[Lieu]](result)))
	}
	
	def byCodeLieu(codeLieu: Int) = Action.async {
		db.LieuCollection.byCodeLieu(codeLieu).map(result => Ok(toJson[Option[Lieu]](result)))
	}
}