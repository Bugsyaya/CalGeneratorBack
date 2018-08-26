package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.ENIModule
import models.ENI.ENIUniteFormation
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class ModuleController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.ModuleCollection.all.map(result => Ok(toJson[Seq[ENIModule]](result)))
	}
	
	def byId(idModule: Int) = Action.async {
		db.ModuleCollection.byId(idModule).map(result => Ok(toJson[Option[ENIModule]](result)))
	}
}