package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.{Promotion, Salle}
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class PromotionController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.PromotionCollection.all.map(result => Ok(toJson[Seq[Promotion]](result)))
	}
	
	def byCodePromotion(codePromotion: String) = Action.async {
		db.PromotionCollection.byCodePromotion(codePromotion).map(result => Ok(toJson[Option[Promotion]](result)))
	}
}