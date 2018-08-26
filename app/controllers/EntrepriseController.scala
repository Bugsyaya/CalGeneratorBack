package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.{ENIEntreprise, ENIUniteFormation}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class EntrepriseController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.EntrepriseCollection.all.map(result => Ok(toJson[Seq[ENIEntreprise]](result)))
	}
	
	def byCodeEntreprise(codeEntreprise: Int) = Action.async {
		db.EntrepriseCollection.byCodeEntreprise(codeEntreprise).map(result => Ok(toJson[Option[ENIEntreprise]](result)))
	}
}
