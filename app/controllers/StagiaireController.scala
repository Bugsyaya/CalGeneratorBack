package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.ENIStagiaire
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class StagiaireController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.StagiaireCollection.all.map(result => Ok(toJson[Seq[ENIStagiaire]](result)))
	}
	
	def byCodeStagiaire(codeStagiaire: Int) = Action.async {
		db.StagiaireCollection.byCodeStagiaire(codeStagiaire).map(result => Ok(toJson[Option[ENIStagiaire]](result)))
	}
}
