package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import models.database.StagiaireCours
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents, Result}

import scala.concurrent.{ExecutionContext, Future}

class StagiaireCoursController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def all = Action.async {
		dbMongo.StagiaireCoursCollection.all
			.map (stagiaireCours => Ok(toJson[Seq[StagiaireCours]](stagiaireCours)))
	}
	
	def byId(idCours: String) = Action.async {
		dbMongo.StagiaireCoursCollection.byId(idCours)
			.map(stagiaireCoursOpt => stagiaireCoursOpt
				.map(stagiaireCours => Ok(toJson[StagiaireCours](stagiaireCours)))
				.getOrElse(InternalServerError(toJson("error")))
			)
	}
}
