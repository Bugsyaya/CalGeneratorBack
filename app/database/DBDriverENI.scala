package database

import java.sql.{Connection, DriverManager, ResultSet}

import scala.concurrent.ExecutionContext.Implicits.global

import com.typesafe.config._

import models.UniteFormation
import models.UniteParFormation
import models.Cours
import models.Formation
import models.Module
import models.ModuleParUnite

import scala.concurrent.Future
import scala.util.control.NonFatal


trait UniteFormationCollection {
	def all: Future[Seq[UniteFormation]]
}

trait UniteParFormationCollection {
	def all: Future[Seq[UniteParFormation]]
}

trait CoursCollection {
	def all: Future[Seq[Cours]]
}

trait FormationCollection {
	def all: Future[Seq[Formation]]
}

trait ModuleCollection {
	def all: Future[Seq[Module]]
}

trait ModuleParUniteCollection {
	def all: Future[Seq[ModuleParUnite]]
}

trait API {
	val UniteFormationCollection: UniteFormationCollection
	val UniteParFormationCollection: UniteParFormationCollection
	val CoursCollection: CoursCollection
	val FormationCollection: FormationCollection
	val ModuleCollection: ModuleCollection
	val ModuleParUniteCollection: ModuleParUniteCollection
}


case class DBDriverENI() {

	val config = ConfigFactory.load()

	val uri = config.getString("db.eni.url")
	val port = config.getString("db.eni.port")

	// Chargement du driver (recommandé par Oracle)
	val driver = Class.forName(config.getString("db.eni.driver"))

	lazy val sqlConn = Future.successful(DriverManager.getConnection(uri))
	
	lazy val defaultDB: Future[Connection] =
		(for {
			db <- sqlConn
		} yield db)
			.recoverWith { case NonFatal(ex) => Future.failed(new RuntimeException(s"SQL server not reachable $uri", ex)) }
	
	def close(): Future[Unit] = {
		println(s"############ Closing DB... ${port} #####################")
		sqlConn.map(_.close())
	}
	
	def query[T](query: String)(implicit imp: ResultSet => T): Future[T] = sqlConn.map(_.prepareStatement(query).executeQuery())
}


