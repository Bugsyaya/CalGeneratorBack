package helper

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import database._
import scala.concurrent.{ExecutionContext, Future}

object API {val db = ENIDB(DBDriverENI(ENIConf()))
	val dbMongo = CalDB(CalConf("localhost", 27017, "CalDatabase"))
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def moduleByFormation(codeFormation: String): Future[Seq[String]] = {
		println(s"codeFormation : '$codeFormation'")
		db.FormationCollection.moduleByCodeFormation(codeFormation).map{oo =>
			println(s"oo : $oo")
			oo.to[collection.immutable.Seq]
		}
	}
}
