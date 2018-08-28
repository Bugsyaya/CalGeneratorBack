package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database.{DBDriverENI, ENIConf, ENIDB}
import models.ENI.ENIPromotion
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class PromotionController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show: Action[AnyContent] = Action.async {
		db.PromotionCollection.all.map(result => Ok(toJson[Seq[ENIPromotion]](result)))
	}
	
	def byCodePromotion(codePromotion: String): Action[AnyContent] = Action.async {
		db.PromotionCollection.byCodePromotion(codePromotion).map(result => Ok(toJson[Option[ENIPromotion]](result)))
	}
}