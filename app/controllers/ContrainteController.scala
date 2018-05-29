//package controllers
//
//import javax.inject.Inject
//
//import akka.actor.ActorSystem
//import akka.stream.ActorMaterializer
//import database.{DBDriverENI, ENIConf, ENIDB}
//import models.choco.ChocoContrainte
//import play.api.libs.json.Json
//import play.api.mvc.{AbstractController, ControllerComponents}
//
//import scala.concurrent.{ExecutionContext, Future}
//
//class ContrainteController @Inject()(cc : ControllerComponents) extends AbstractController(cc) {
//	val db = ENIDB(DBDriverENI(ENIConf()))
//
//	implicit val system: ActorSystem = ActorSystem()
//	implicit val ec: ExecutionContext = system.dispatcher
//	implicit val mat: ActorMaterializer = ActorMaterializer()
//
//	def generationCalendrier = Action.async { request =>
//		request.body.asJson.map{response =>
//			Json.fromJson[ChocoContrainte](response).map{chocoContrainte =>
//
//			}
//		}
//
//		Future.successful(Ok(""))
//	}
//}
