package database

import java.sql.{Connection, DriverManager, ResultSet}

import helper.Utils
import models.ENI._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.sys.env
import scala.util.control.NonFatal


trait UniteFormationCollection {
	def all: Future[Seq[ENIUniteFormation]]
	
	def byId(idUniteFormation: Int): Future[Option[ENIUniteFormation]]
}

trait ModuleCollection {
	def all: Future[Seq[ENIModule]]
	
	def byId(idModule: Int): Future[Option[ENIModule]]
	
	def byDateAndFormation(debut: String, fin: String, codeFormation: String): Future[Seq[Int]]
}

trait ModuleParUniteCollection {
	def all: Future[Seq[ENIModuleParUnite]]
	
	def byId(idModuleParUnite: Int): Future[Option[ENIModuleParUnite]]
	
	def byIdModule(idModule: Int): Future[Seq[ENIModuleParUnite]]
	
	def byIdUnite(idUnite: Int): Future[Seq[ENIModuleParUnite]]
}

trait SalleCollection {
	def all: Future[Seq[ENISalle]]
	
	def byCodeSalle(codeSalle: String): Future[Option[ENISalle]]
}

trait CoursCollection {
	def all: Future[Seq[ENICours]]
	
	def byId(id: String): Future[Option[ENICours]]
	
	def byDateAndModule(debut: String, fin: String, idModule: Int): Future[Seq[String]]
	
	def byModule(idModule: Int): Future[Seq[String]]
}

trait EntrepriseCollection {
	def all: Future[Seq[ENIEntreprise]]
	
	def byCodeEntreprise(codeEntreprise: Int): Future[Option[ENIEntreprise]]
}

trait FormationCollection {
	def all: Future[Seq[ENIFormation]]
	
	def byCodeFormation(codeEntreprise: String): Future[Option[ENIFormation]]
	
	def moduleByCodeFormation(codeFormation: String): Future[Seq[String]]
}

trait LieuCollection {
	def all: Future[Seq[ENILieu]]
	
	def byCodeLieu(codeLieu: Int): Future[Option[ENILieu]]
}

trait PromotionCollection {
	def all: Future[Seq[ENIPromotion]]
	
	def byCodePromotion(codePromotion: String): Future[Option[ENIPromotion]]
}

trait StagiaireCollection {
	def all: Future[Seq[ENIStagiaire]]
	
	def byCodeStagiaire(codeStagiaire: Int): Future[Option[ENIStagiaire]]
}

trait TitreCollection {
	def all: Future[Seq[ENITitre]]
	
	def byCodeTitre(codeTitre: String): Future[Option[ENITitre]]
}

trait PlanningIndividuelFormationCollection {
	def all: Future[Seq[ENIPlanningIndividuelFormation]]
	
	def byCodePlanning(codePlanning: Int): Future[Option[ENIPlanningIndividuelFormation]]
}

trait StagiaireParEntrepriseCollection {
	def all: Future[Seq[ENIStagiaireParEntreprise]]
	
	def byCodeStagiaire(codeStagiaire: Int): Future[Seq[ENIStagiaireParEntreprise]]
	
	def byCodeEntreprise(codeEntreprise: Int): Future[Seq[ENIStagiaireParEntreprise]]
	
	def byTitreVise(titreVise: String): Future[Seq[ENIStagiaireParEntreprise]]
	
	def byCodeStagiaireAndCodeEntreprise(codeStagiaire: Int, codeEntreprise: Int): Future[Option[ENIStagiaireParEntreprise]]
}

trait UniteParFormationCollection {
	def all: Future[Seq[ENIUniteParFormation]]
	
	def byCodeFormation(codeFormation: String): Future[Seq[ENIUniteParFormation]]
	
	def byId(id: Int): Future[Seq[ENIUniteParFormation]]
	
	def byIdUniteFormation(idUniteFormation: Int): Future[Seq[ENIUniteParFormation]]
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

class ENIConf {
      val host: String = env.getOrElse("SQLSERVER_HOST", "localhost")
      val port: Int = Integer.parseInt(env.getOrElse("SQLSERVER_PORT", "1433"))
      val dbName: String = env.getOrElse("SQLSERVER_DATABASE", "master")
      val login: String = env.getOrElse("SQLSERVER_LOGIN", "sa")
      val password: String = env.getOrElse("SQLSERVER_PASSWORD","yourStrong(!)Password")
}
object ENIConf {
	val config = new ENIConf
	def apply(): ENIConf = config
}

case class DBDriverENI(conf: ENIConf) {
	val uri = s"""jdbc:sqlserver://${conf.host}:${conf.port};databaseName=${conf.dbName};user=${conf.login};password=${conf.password};"""
	
	lazy val sqlConn: Future[Connection] = Future.successful(DriverManager.getConnection(uri))
	
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
	
	def queryBasic(query: String, index: String): Future[Seq[String]] = {
		sqlConn.map(_.prepareStatement(query).executeQuery()).map { rs =>
			Utils.results(rs)(r => r.getString(index)).toSeq
		}
	}
}


