package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIDB}
import models.{Cours, Entreprise, Formation, UniteFormation}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext
import play.api.libs.json.Json.toJson

class FormationController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI())

	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.FormationCollection.all.map(result => Ok(toJson[Seq[Formation]](result)))
	}

	def byCodeFormation(codeFormation: String) = Action.async {
		db.FormationCollection.byCodeFormation(codeFormation).map(result => Ok(toJson[Option[Formation]](result)))
	}
}
