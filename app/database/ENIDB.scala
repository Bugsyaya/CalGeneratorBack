package database

import models.{Module, ModuleParUnite, Salle, UniteFormation}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ENIDB(driver: DBDriverENI) extends API {
	val UniteFormationCollection = new UniteFormationCollection {
		override def all: Future[Seq[UniteFormation]] = driver.query("SELECT * FROM UniteFormation")
		
		override def byId(idUniteFormation: Int): Future[Option[UniteFormation]] = driver.query[Seq[UniteFormation]](s"SELECT * FROM UniteFormation WHERE idUniteFormation=$idUniteFormation").map(_.headOption)
	}
	
	val ModuleCollection = new ModuleCollection {
		override def all: Future[Seq[Module]] = driver.query("SELECT * FROM Module")
		
		override def byId(idModule: Int): Future[Option[Module]] = driver.query[Seq[Module]](s"SELECT * FROM Module WHERE idModule=$idModule").map(_.headOption)
	}
	
	val ModuleParUniteCollection = new ModuleParUniteCollection {
		override def all: Future[Seq[ModuleParUnite]] = driver.query("SELECT * FROM ModuleParUnite")
		
		override def byId(id: Int): Future[Option[ModuleParUnite]] = driver.query[Seq[ModuleParUnite]](s"SELECT * FROM ModuleParUnite WHERE id=$id").map(_.headOption)
		
		override def byIdModule(idModule: Int): Future[Seq[ModuleParUnite]] = driver.query[Seq[ModuleParUnite]](s"SELECT * FROM ModuleParUnite WHERE idModule=$idModule")
		
		override def byIdUnite(idUnite: Int): Future[Seq[ModuleParUnite]] = driver.query[Seq[ModuleParUnite]](s"SELECT * FROM ModuleParUnite WHERE idUnite=$idUnite")
	}
	
	val SalleCollection = new SalleCollection {
		override def all: Future[Seq[Salle]] = driver.query("SELECT * FROM Salle")
		
		override def byCodeSalle(codeSalle: String): Future[Option[Salle]] = driver.query[Seq[Salle]](s"SELECT * FROM Salle WHERE codeSalle='$codeSalle'").map(_.headOption)
	}
}
