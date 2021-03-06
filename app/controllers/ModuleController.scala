package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.{ENICours, ENIModule}
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ModuleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show: Action[AnyContent] = Action.async {
		db.ModuleCollection.all.map(result => Ok(toJson[Seq[ENIModule]](result)))
	}
	
	def byId(idModule: Int): Action[AnyContent] = Action.async {
		db.ModuleCollection.byId(idModule).map(result => Ok(toJson[Option[ENIModule]](result)))
	}
	
	def coursByIdModule(idModule: Int): Action[AnyContent] = Action.async {
		db.CoursCollection.byModule(idModule).flatMap { idsCours =>
			for {
				result <- Future.sequence(idsCours.map{idCours =>
					db.CoursCollection.byId(idCours)
				}).map(o => o.filter(_.isDefined).map(p =>p.get))
			} yield Ok(toJson[Seq[ENICours]](result))
		}
	}
}