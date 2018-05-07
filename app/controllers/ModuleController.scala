package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIDB}
import models.{Module, UniteFormation}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class ModuleController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.ModuleCollection.all.map(result => Ok(toJson[Seq[Module]](result)))
	}
	
	def byId(idModule: Int) = Action.async {
		db.ModuleCollection.byId(idModule).map(result => Ok(toJson[Option[Module]](result)))
	}
}