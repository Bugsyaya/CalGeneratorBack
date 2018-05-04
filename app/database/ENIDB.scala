package database

import models.{Module, UniteFormation}

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
}
