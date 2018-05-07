package database

import java.sql.{Connection, DriverManager, ResultSet}
import scala.concurrent.ExecutionContext.Implicits.global
import models._

import com.typesafe.config._

import scala.concurrent.Future
import scala.util.control.NonFatal


trait UniteFormationCollection {
	def all: Future[Seq[UniteFormation]]
	def byId(idUniteFormation: Int): Future[Option[UniteFormation]]
}
trait ModuleCollection {
	def all: Future[Seq[Module]]
	def byId(idModule: Int): Future[Option[Module]]
}
trait ModuleParUniteCollection {
	def all: Future[Seq[ModuleParUnite]]
	def byId(idModuleParUnite: Int): Future[Option[ModuleParUnite]]
	def byIdModule(idModule: Int): Future[Seq[ModuleParUnite]]
	def byIdUnite(idUnite: Int): Future[Seq[ModuleParUnite]]
}
trait SalleCollection {
	def all: Future[Seq[Salle]]
	def byCodeSalle(codeSalle: String): Future[Option[Salle]]
}
trait CoursCollection {
	def all: Future[Seq[Cours]]
	def byId(id: String): Future[Option[Cours]]
}
trait EntrepriseCollection {
	def all: Future[Seq[Entreprise]]
	def byCodeEntreprise(codeEntreprise: Int): Future[Option[Entreprise]]
}
trait FormationCollection {
	def all: Future[Seq[Formation]]
	def byCodeFormation(codeEntreprise: String): Future[Option[Formation]]
}
trait LieuCollection {
	def all: Future[Seq[Lieu]]
	def byCodeLieu(codeLieu: Int): Future[Option[Lieu]]
}

trait UniteParFormationCollection {
	def all: Future[Seq[UniteParFormation]]
}


trait API {
	val UniteFormationCollection: UniteFormationCollection
	val UniteParFormationCollection: UniteParFormationCollection
	val FormationCollection: FormationCollection
	val ModuleCollection: ModuleCollection
	val ModuleParUniteCollection: ModuleParUniteCollection
	val SalleCollection: SalleCollection
	val CoursCollection: CoursCollection
	val EntrepriseCollection: EntrepriseCollection
	val LieuCollection: LieuCollection
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


