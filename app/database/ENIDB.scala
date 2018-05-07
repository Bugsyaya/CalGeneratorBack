package database

import models.UniteFormation
import models.UniteParFormation
import models.Formation
import models.Cours
import models.Module
import models.ModuleParUnite

import scala.concurrent.Future

case class ENIDB(driver: DBDriverENI) extends API {
	val UniteFormationCollection = new UniteFormationCollection {
		override def all: Future[Seq[UniteFormation]] = driver.query("SELECT * FROM UniteFormation")
	}

	val UniteParFormationCollection = new UniteParFormationCollection {
		override def all: Future[Seq[UniteParFormation]] = driver.query("SELECT * FROM UniteParFormation")
	}

  val FormationCollection = new FormationCollection {
    override def all: Future[Seq[Formation]] = driver.query("SELECT * FROM Formation")
  }

  val CoursCollection = new CoursCollection {
    override def all: Future[Seq[Cours]] = driver.query("SELECT * FROM Cours")
  }

  val ModuleCollection = new ModuleCollection {
    override def all: Future[Seq[Module]] = driver.query("SELECT * FROM Module")
  }

  val ModuleParUniteCollection = new ModuleParUniteCollection {
    override def all: Future[Seq[ModuleParUnite]] = driver.query("SELECT * FROM ModuleParUnite")
  }

}
