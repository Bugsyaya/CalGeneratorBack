package database

import models.Calendrier
import reactivemongo.api._
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt


case class CalConf(
	                  host: String = "127.0.0.1",
	                  port: Int = 27017,
	                  dbName: String = "CalDatabase"
                  )

case class CalDB(conf: CalConf) extends APICal{
	val uri = s"""mongodb://${conf.host}:${conf.port}/${conf.dbName}"""
	
	lazy val mongoConn = Future.fromTry(MongoConnection.parseURI(uri).map(MongoDriver().connection))
	
	lazy val defaultDB: Future[DefaultDB] =
		(for {
			con <- mongoConn
			db <- con.database(conf.dbName)
		} yield db)
			.recoverWith { case NonFatal(ex) => Future.failed(new RuntimeException(s"MongoDb not reachable $uri", ex)) }
	
	override def close(): Future[Unit] = {
		println(s"############ Closing DB... ${conf.port} #####################")
		mongoConn
			.flatMap(_.askClose()(60.seconds))
			.map(_ => println(s"############ Closing DB!!! ${conf.port} #####################"))
	}
	
	val CalendrierCollection = new CalendrierCollection {
		private def collectionCalendrier: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("calendrier"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def save(calendrier: Calendrier): Future[WriteResult] = {
			for {
				collection <- collectionCalendrier
				created <- collection.insert[Calendrier](calendrier)
			} yield created
		}
	}
}