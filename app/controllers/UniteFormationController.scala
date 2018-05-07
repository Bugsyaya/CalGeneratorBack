package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIDB}
import javax.inject.Inject
import models.UniteFormation
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class UniteFormationController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.UniteFormationCollection.all.map(result => Ok(toJson[Seq[UniteFormation]](result)))
	}
	
	def byId(idUniteFormation: Int) = Action.async {
		db.UniteFormationCollection.byId(idUniteFormation).map(result => Ok(toJson[Option[UniteFormation]](result)))
	}
}
