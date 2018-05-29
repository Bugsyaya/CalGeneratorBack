package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models._
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json.toJson

class FormationController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.FormationCollection.all.map(result => Ok(toJson[Seq[Formation]](result)))
	}
	
	def byCodeFormation(codeFormation: String) = Action.async {
		db.FormationCollection.byCodeFormation(codeFormation).map(result => Ok(toJson[Option[Formation]](result)))
	}
	
	def moduleByCodeFormation(codeFormation: String) = Action.async {
		db.FormationCollection.moduleByCodeFormation(codeFormation).flatMap{modules =>
			Future.sequence(modules.map { module =>
				db.ModuleCollection.byId(module.toInt)
					.filter(_.isDefined)
					.map(_.get)
			}).map(m => Ok(toJson[Seq[Module]](m)))
		}
	}
}