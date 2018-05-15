package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.Titre
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class TitreController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.TitreCollection.all.map(result => Ok(toJson[Seq[Titre]](result)))
	}
	
	def byCodeTitre(codeTitre: String) = Action.async {
		db.TitreCollection.byCodeTitre(codeTitre).map(result => Ok(toJson[Option[Titre]](result)))
	}
}