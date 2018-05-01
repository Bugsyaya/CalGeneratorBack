package database

import models.UniteFormation

import scala.concurrent.Future

case class ENIDB(driver: DBDriverENI) extends API {
	val UniteFormationCollection = new UniteFormationCollection {
		override def all: Future[Seq[UniteFormation]] = driver.query("SELECT * FROM UniteFormation")
	}
}
