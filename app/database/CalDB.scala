package database

import models.Front.FrontProblem
import models.choco.{ChocoConstraint, ChocoModule}
import models.database.{Constraint, ConstraintModule, ForChocoModule}
import models.{Calendrier, ModuleFormation}
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
		
		override def create(problem: FrontProblem): Future[WriteResult] =
			for {
				collection <- collectionProblem
				created <- collection.insert[FrontProblem](problem)
			} yield created
		
		override def update(problem: FrontProblem) : Future[WriteResult] =
			for {
				collection <- collectionProblem
				update <- collection.update(JsObject(Seq("formationId" -> JsString(problem.idProblem.getOrElse("")))), problem)
			} yield update
		
		override def byId(idProblem: String): Future[Option[FrontProblem]] =
			for {
				collection <- collectionProblem
				result <- collection.find[JsObject](JsObject(Seq("idProblem" -> JsString(idProblem)))).one[FrontProblem]
			} yield result
	}
	
	val ConstraintModuleCollection = new ConstraintModuleCollection {
		private def collectionModuleContrainte: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("moduleContrainte"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def save(chocoModules: Seq[ConstraintModule]): Future[Seq[WriteResult]] =
			for {
				collection <- collectionModuleContrainte
				created <- Future.sequence(chocoModules.map(chocoModule => collection.insert[ConstraintModule](chocoModule)))
			} yield created
		
		override def byId(chocoModuleId: Int): Future[Option[ConstraintModule]] =
			for {
				collection <- collectionModuleContrainte
				result <- collection.find(JsObject(Seq("idModule" -> JsNumber(chocoModuleId)))).one[ConstraintModule]
			} yield result
		
		def byIdAndFormation(idModule: Int, codeFormation: String): Future[Option[ConstraintModule]] =
			for {
				collection <- collectionModuleContrainte
				result <- collection.find(JsObject(Seq("idModule" -> JsNumber(idModule), "codeFormation" -> JsString(codeFormation)))).one[ConstraintModule]
			} yield result
	}
	
	val ConstraintCollection = new ConstraintCollection {
		private def constraintCollection: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("contraint"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def create(constraint: Constraint): Future[WriteResult] =
			for {
				collection <- constraintCollection
				created <- collection.insert[Constraint](constraint)
			} yield created
		
		override def byId(constraintId: String): Future[Option[Constraint]] =
			for {
				collection <- constraintCollection
				result <- collection.find(JsObject(Seq("idConstraint" -> JsString(constraintId)))).one[Constraint]
			} yield result
		
		override def all: Future[Seq[Constraint]] =
			for {
				collection <- constraintCollection
				result <- collection
					.find[JsObject](JsObject(Seq.empty[(String, JsValue)]))
					.cursor[Constraint]()
					.collect[Seq]()
			} yield result
	}
	
	val ChocoConstraintCollection = new ChocoConstraintCollection {
		private def collectionContrainte: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("contraint"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def create(chocoConstraint: ChocoConstraint): Future[WriteResult] =
			for {
				collection <- collectionContrainte
				created <- collection.insert[ChocoConstraint](chocoConstraint)
			} yield created
		
		override def byId(chocoConstraintId: String): Future[Option[ChocoConstraint]] =
			for {
				collection <- collectionContrainte
				find <- collection.find(JsObject(Seq("idConstraint" -> JsString(chocoConstraintId)))).one[ChocoConstraint]
			} yield find
	}
	
	val ForChocoModule = new ForChocModule {
		private def collectionContrainte: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("contraint"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def save(forChocoModule: ForChocoModule): Future[WriteResult] =
			for {
				collection <- collectionContrainte
				created <- collection.insert[ForChocoModule](forChocoModule)
			} yield created
		
		override def byIdModuleAndCodeFormation(idModule: Int, codeFormation: String): Future[Option[ChocoModule]] =
			for {
				collection <- collectionContrainte
				find <- collection.find(JsObject(Seq("idModule" -> JsNumber(idModule), "codeFormation" -> JsString(codeFormation)))).one[ChocoModule]
			} yield find
		
		override def byId(idForChocoModule: String): Future[Option[ForChocModule]] =
			for {
				collection <- collectionContrainte
				find <- collection.find(JsObject(Seq("idForChocoModule" -> JsString(idForChocoModule)))).one[ForChocModule]
			} yield find
	}
}