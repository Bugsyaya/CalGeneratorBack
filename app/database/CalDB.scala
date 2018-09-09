package database

import java.lang.Integer.parseInt

import models.Front.{FrontModulePrerequis, FrontModulePrerequisPlanning, FrontProblem}
import models.choco.Constraint.Entree.ChocoConstraint
import models.database.{Constraint, ConstraintModule, StagiaireCours}
import models.{Calendrier, ModuleFormation}
import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}
import play.modules.reactivemongo.json._
import reactivemongo.api._
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.sys.env
import scala.util.control.NonFatal


class CalConf {
	println(s"env : $env")
	val host: String = env.getOrElse("MONGO_HOST", "localhost")
	val port: Int = parseInt(env.getOrElse("MONGO_PORT", "27017"))
	val dbName: String = env.getOrElse("MONGO_DATABASE", "CalDatabase")
}
object CalConf {
	private val config = new CalConf()
	def apply(): CalConf = config
}

case class CalDB(conf: CalConf) extends APICal {
	val uri = s"""mongodb://${conf.host}:${conf.port}/${conf.dbName}"""
	
	lazy val mongoConn: Future[MongoConnection] = Future.fromTry(MongoConnection.parseURI(uri).map(MongoDriver().connection))
	
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
		
		override def update(calendrier: Calendrier): Future[WriteResult] = {
			for {
				collection <- collectionCalendrier
				update <- collection.update(JsObject(Seq("idCalendrier" -> JsString(calendrier.idCalendrier))), calendrier)
			} yield update
		}
		
		override def all: Future[Seq[Calendrier]] =
			for {
				collection <- collectionCalendrier
				result <- collection
					.find[JsObject](JsObject(Seq.empty[(String, JsValue)]))
					.cursor[Calendrier]()
					.collect[Seq]()
			} yield result
		
		override def byId(idCalendar: String): Future[Option[Calendrier]] =
			for {
				collection <- collectionCalendrier
				result <- collection.find(JsObject(Seq("idCalendrier" -> JsString(idCalendar)))).one[Calendrier]
			} yield result
		
		override def byStatus(status: String): Future[Seq[Calendrier]] =
			for {
				collection <- collectionCalendrier
				result <- collection.find(JsObject(Seq("status" -> JsString(status))))
					.cursor[Calendrier]()
					.collect[Seq]()
			} yield result
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
		
		//		override def update(problem: FrontProblem) : Future[WriteResult] =
		//			for {
		//				collection <- collectionProblem
		//				update <- collection.update(JsObject(Seq("formationId" -> JsString(problem.idProblem.getOrElse("")))), problem)
		//			} yield update
		
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
	
	val ModulePrerequisCollection = new ModulePrerequisCollection {
		private def collectionModulePrerequis: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("modulePrerequis"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def byId(id: String): Future[Option[FrontModulePrerequis]] =
			for {
				collection <- collectionModulePrerequis
				result <- collection.find(JsObject(Seq("idModulePrerequis" -> JsString(id)))).one[FrontModulePrerequis]
			} yield result
		
		override def create(frontModulePrerequis: FrontModulePrerequis): Future[WriteResult] =
			for {
				collection <- collectionModulePrerequis
				created <- collection.insert[FrontModulePrerequis](frontModulePrerequis)
			} yield created
		
		override def all: Future[Seq[FrontModulePrerequis]] =
			for {
				collection <- collectionModulePrerequis
				result <- collection
					.find[JsObject](JsObject(Seq.empty[(String, JsValue)]))
					.cursor[FrontModulePrerequis]()
					.collect[Seq]()
			} yield result
		
		override def update(frontModulePrerequis: FrontModulePrerequis): Future[WriteResult] =
			for {
				collection <- collectionModulePrerequis
				update <- collection.update(JsObject(Seq("idModulePrerequis" -> JsString(frontModulePrerequis.idModulePrerequis))), frontModulePrerequis)
			} yield update
		
		override def byFormationAndModule(codeFormation: String, idModule: Int): Future[Option[FrontModulePrerequis]] =
			for {
				collection <- collectionModulePrerequis
				result <- collection.find(JsObject(Seq("codeFormation" -> JsString(codeFormation), "idModule" -> JsNumber(idModule)))).one[FrontModulePrerequis]
			} yield result
	}
	
	val StagiaireCoursCollection = new StagiaireCoursCollection {
		private def collectionStagiaireCours: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("stagiaireCours"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def byId(id: String): Future[Option[StagiaireCours]] =
			for {
				collection <- collectionStagiaireCours
				result <- collection.find(JsObject(Seq("idCours" -> JsString(id)))).one[StagiaireCours]
			} yield result
		
		override def create(stagiaireCours: StagiaireCours): Future[WriteResult] =
			for {
				collection <- collectionStagiaireCours
				created <- collection.insert[StagiaireCours](stagiaireCours)
			} yield created
		
		override def all: Future[Seq[StagiaireCours]] =
			for {
				collection <- collectionStagiaireCours
				result <- collection
					.find[JsObject](JsObject(Seq.empty[(String, JsValue)]))
					.cursor[StagiaireCours]()
					.collect[Seq]()
			} yield result
		
		override def update(stagiaireCours: StagiaireCours): Future[WriteResult] =
			for {
				collection <- collectionStagiaireCours
				update <- collection.update(JsObject(Seq("idCours" -> JsString(stagiaireCours.idCours))), stagiaireCours)
			} yield update
	}
	
	val ModulePrerequisPlanningCollection = new ModulePrerequisPlanningCollection {
		private def collectionModulePrerequisPlanning: Future[JSONCollection] =
			defaultDB
				.map(_.collection[JSONCollection]("modulePrerequisPlanning"))
				.recover {
					case e: Exception =>
						throw new RuntimeException("db not reachable")
				}
		
		override def byId(id: String): Future[Option[FrontModulePrerequisPlanning]] =
			for {
				collection <- collectionModulePrerequisPlanning
				result <- collection.find(JsObject(Seq("idModulePrerequisPlanning" -> JsString(id)))).one[FrontModulePrerequisPlanning]
			} yield result
		
		override def create(frontModulePrerequisPlanning: FrontModulePrerequisPlanning): Future[WriteResult] =
			for {
				collection <- collectionModulePrerequisPlanning
				created <- collection.insert[FrontModulePrerequisPlanning](frontModulePrerequisPlanning)
			} yield created
		
		override def all: Future[Seq[FrontModulePrerequisPlanning]] =
			for {
				collection <- collectionModulePrerequisPlanning
				result <- collection
					.find[JsObject](JsObject(Seq.empty[(String, JsValue)]))
					.cursor[FrontModulePrerequisPlanning]()
					.collect[Seq]()
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
			} yield {
				find
			}
	}
}