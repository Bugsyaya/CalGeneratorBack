package controllers

import java.util.UUID
import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import models.choco.ChocoConstraint
import models.database.Constraint
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class ContrainteController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))

	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()

	def create = Action.async { request =>
		request.body.asJson.map{response =>
			println(response)
			println(Json.fromJson[ChocoConstraint](response))
			Json.fromJson[ChocoConstraint](response).map{chocoContrainte =>
				val cc = Constraint(
					UUID.randomUUID().toString,
					chocoContrainte.place,
					chocoContrainte.annualNumberOfHour,
					chocoContrainte.maxDurationOfTraining,
					chocoContrainte.trainingFrequency,
					chocoContrainte.maxStudentInTraining,
					chocoContrainte.listStudentRequired,
					chocoContrainte.listPeriodeOfTrainingExclusion,
					chocoContrainte.listPeriodeOfTrainingInclusion,
					chocoContrainte.prerequisModule
				)

				dbMongo.ConstraintCollection.create(cc).map{wr =>
					if(wr.n > 0) Ok(Json.toJson[Constraint](cc))
					else InternalServerError("Il y a eu une erreur")
				}
			}.getOrElse(Future.successful(BadRequest("Il manque des param")))
		}.getOrElse(Future.successful(BadRequest("Y a un pb")))
	}
	
	def byId(idConstraint: String) = Action.async{
		dbMongo.ConstraintCollection.byId(idConstraint).map{constraintOpt =>
			constraintOpt.map{constraint =>
				Ok(Json.toJson[Constraint](constraint))
			}.getOrElse(Ok("Not found"))
		}
	}
	
	def all = Action.async{
		dbMongo.ConstraintCollection.all.map{constraints =>
			Ok(Json.toJson[Seq[Constraint]](constraints))
		}
	}
}
