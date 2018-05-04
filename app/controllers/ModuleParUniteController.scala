package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.{ModuleParUnite, UniteFormation}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class ModuleParUniteController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.ModuleParUniteCollection.all.map(result => Ok(toJson[Seq[ModuleParUnite]](result)))
	}
	
	def byId(id: Int) = Action.async {
		db.ModuleParUniteCollection.byId(id).map(result => Ok(toJson[Option[ModuleParUnite]](result)))
	}
	
	def byIdModule(idModule: Int) = Action.async {
		db.ModuleParUniteCollection.byIdModule(idModule).map(result => Ok(toJson[Seq[ModuleParUnite]](result)))
	}
	
	def byIdUnite(idUnite: Int) = Action.async {
		db.ModuleParUniteCollection.byIdUnite(idUnite).map(result => Ok(toJson[Seq[ModuleParUnite]](result)))
	}
}