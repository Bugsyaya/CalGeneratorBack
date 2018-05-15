package database

import java.sql.{Connection, DriverManager, ResultSet}

import scala.concurrent.ExecutionContext.Implicits.global
import models._

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
trait PromotionCollection {
	def all: Future[Seq[Promotion]]
	def byCodePromotion(codePromotion: String): Future[Option[Promotion]]
}
trait StagiaireCollection {
	def all: Future[Seq[Stagiaire]]
	def byCodeStagiaire(codeStagiaire: Int): Future[Option[Stagiaire]]
}
trait TitreCollection {
	def all: Future[Seq[Titre]]
	
	def byCodeTitre(codeTitre: String): Future[Option[Titre]]
}
trait PlanningIndividuelFormationCollection {
	def all: Future[Seq[PlanningIndividuelFormation]]
	def byCodePlanning(codePlanning: Int): Future[Option[PlanningIndividuelFormation]]
}
trait StagiaireParEntrepriseCollection {
	def all: Future[Seq[StagiaireParEntreprise]]
	def byCodeStagiaire(codeStagiaire: Int): Future[Seq[StagiaireParEntreprise]]
	def byCodeEntreprise(codeEntreprise: Int): Future[Seq[StagiaireParEntreprise]]
	def byTitreVise(titreVise: String): Future[Seq[StagiaireParEntreprise]]
	def byCodeStagiaireAndCodeEntreprise(codeStagiaire: Int, codeEntreprise: Int): Future[Option[StagiaireParEntreprise]]
}
trait UniteParFormationCollection {
	def all: Future[Seq[UniteParFormation]]
	def byCodeFormation(codeFormation: String): Future[Seq[UniteParFormation]]
	def byId(id: Int): Future[Seq[UniteParFormation]]
	def byIdUniteFormation(idUniteFormation: Int): Future[Seq[UniteParFormation]]
}

trait API {
	val UniteFormationCollection: UniteFormationCollection
	val ModuleCollection: ModuleCollection
	val ModuleParUniteCollection: ModuleParUniteCollection
	val SalleCollection: SalleCollection
	val CoursCollection: CoursCollection
	val EntrepriseCollection: EntrepriseCollection
	val FormationCollection: FormationCollection
	val LieuCollection: LieuCollection
	val PromotionCollection: PromotionCollection
	val StagiaireCollection: StagiaireCollection
	val TitreCollection: TitreCollection
	val PlanningIndividuelFormationCollection: PlanningIndividuelFormationCollection
	val StagiaireParEntrepriseCollection: StagiaireParEntrepriseCollection
	val UniteParFormationCollection: UniteParFormationCollection
}

case class ENIConf(
	                  host: String = "localhost",
	                  port: Int = 1433,
	                  dbName: String = "master",
	                  login: String = "sa",
	                  password: String = "yourStrong(!)Password"
                  )

case class DBDriverENI(conf: ENIConf) {
	val uri = s"""jdbc:sqlserver://${conf.host}:${conf.port};databaseName=${conf.dbName};user=${conf.login};password=${conf.password};"""
	
	lazy val sqlConn = Future.successful(DriverManager.getConnection(uri))
	
	lazy val defaultDB: Future[Connection] =
		(for {
			db <- sqlConn
		} yield db)
			.recoverWith { case NonFatal(ex) => Future.failed(new RuntimeException(s"MongoDb not reachable $uri", ex)) }
	
	def close(): Future[Unit] = {
		println(s"############ Closing DB... ${conf.port} #####################")
		sqlConn.map(_.close())
	}
	
	def query[T](query: String)(implicit imp: ResultSet => T): Future[T] = sqlConn.map(_.prepareStatement(query).executeQuery())
}


