package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIDB}
import javax.inject.Inject
import models.Module
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class CalGeneratorController @Inject()(cc : ControllerComponents) extends AbstractController(cc){

	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	

}
