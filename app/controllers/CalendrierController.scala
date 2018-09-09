package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import models.Calendrier
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

class CalendrierController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def alertCalendar: Action[AnyContent] = Action.async {
		for {
			calendriersM <- db.CoursCollection.all.flatMap { coursSeq =>
				Future.sequence(coursSeq.to[scala.collection.immutable.Seq].map { cours =>
					dbMongo.CalendrierCollection.all.flatMap { calendriers =>
						Future.sequence(calendriers.map { calendrier =>
							val p = calendrier.cours.filter { calendrierCours =>
								calendrierCours.idCours == cours.idCours &&
									(calendrierCours.debut != cours.debut || calendrierCours.fin != cours.fin || calendrierCours.codeLieu != cours.codeLieu)
							}.map { calendrierCours =>
								calendrierCours.copy(alerteModification = Some(s"Changement détecté sur ${if (calendrierCours.debut != cours.debut) "la date de début" else ""} ${if (calendrierCours.fin != cours.fin) "la date de fin" else ""} ${if (calendrierCours.codeLieu != cours.codeLieu) "le lieu" else ""}"))
							}
							
							val allCoursCal = p ++ calendrier.cours.filterNot(c => p.map(_.idCours).contains(c.idCours))
							
							if (p.nonEmpty) {
								val updatedCal = calendrier.copy(status = "alerte", cours = allCoursCal)
								dbMongo.CalendrierCollection.update(updatedCal).map(_ => Some(updatedCal))
							} else Future.successful(None)
						}).map(uuu => uuu.filter(_.isDefined).map(_.get))
					}
				}).map(_.flatMap(o => o))
			}
		} yield Ok(toJson[Seq[Calendrier]](calendriersM))
	}
	
	def alertStatus(status: String): Action[AnyContent] = Action.async {
		dbMongo.CalendrierCollection.byStatus(status).map { calendriers =>
			println(s"calendriers : ${calendriers.size}")
			Ok(toJson(calendriers.size))
		}
	}
	
	def all = Action.async{
		dbMongo.CalendrierCollection.all.map{calendriers =>
			Ok(toJson[Seq[Calendrier]](calendriers))
		}
	}
	
	def byId(id: String) = Action.async{
		dbMongo.CalendrierCollection.byId(id).map{calendrier =>
			Ok(toJson[Option[Calendrier]](calendrier))
		}
	}
	
}
