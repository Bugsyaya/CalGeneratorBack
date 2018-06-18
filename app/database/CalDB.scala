package database

import models.choco.{ChocoConstraint, ChocoModule}
import models.{Calendrier, ModuleFormation, Problem}
import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import reactivemongo.api._
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection
import play.modules.reactivemongo.json._

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
	
	val ModuleFormationCollection = new ModuleFormationCollection {
		private def collectionModuleFormation: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("moduleFormation"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def save(moduleFormation: ModuleFormation): Future[WriteResult] =
			for {
				collection <- collectionModuleFormation
				created <- collection.insert[ModuleFormation](moduleFormation)
			} yield created
		
		override def byId(idModuleFormation: String): Future[Option[ModuleFormation]] =
			for {
				collection <- collectionModuleFormation
				result <- collection.find(JsObject(Seq("formationId" -> JsString(idModuleFormation)))).one[ModuleFormation]
			} yield result
	}
	
	val ProblemCollection = new ProblemCollection {
		private def collectionProblem: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("problem"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def create(problem: Problem): Future[WriteResult] =
			for {
				collection <- collectionProblem
				created <- collection.insert[Problem](problem)
			} yield created
		
		override def update(problem: Problem) : Future[WriteResult] =
			for {
				collection <- collectionProblem
				update <- collection.update(JsObject(Seq("formationId" -> JsString(problem.idProblem))), problem)
			} yield update
		
		override def byId(idProblem: String): Future[Option[Problem]] =
			for {
				collection <- collectionProblem
				result <- collection.find[JsObject](JsObject(Seq("idProblem" -> JsString(idProblem)))).one[Problem]
			} yield result
	}
	
	val ModuleContrainteCollection = new ModuleContrainteCollection {
		private def collectionModuleContrainte: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("moduleContrainte"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def save(chocoModules: Seq[ChocoModule]): Future[Seq[WriteResult]] =
			for {
				collection <- collectionModuleContrainte
				created <- Future.sequence(chocoModules.map(chocoModule => collection.insert[ChocoModule](chocoModule)))
			} yield created
		
		def byId(idModule: Int): Future[Option[ChocoModule]] =
			for {
				collection <- collectionModuleContrainte
				result <- collection.find(JsObject(Seq("formationId" -> JsNumber(idModule)))).one[ChocoModule]
			} yield result
	}
	
	val ChocoConstraintCollection = new ChocoConstraintCollection {
		private def collectionModuleContrainte: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("contraint"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def create(chocoConstraint: ChocoConstraint): Future[WriteResult] =
			for {
				collection <- collectionModuleContrainte
				created <- collection.insert[ChocoConstraint](chocoConstraint)
			} yield created
	}
}