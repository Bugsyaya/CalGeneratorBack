package database

import models._

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
	
	val CoursCollection = new CoursCollection {
		override def all: Future[Seq[Cours]] = driver.query("SELECT * FROM Cours")
		
		override def byId(id: String): Future[Option[Cours]] = driver.query[Seq[Cours]](s"SELECT * FROM Cours WHERE idCours='$id'").map(_.headOption)
	}
	
	val EntrepriseCollection = new EntrepriseCollection {
		override def all: Future[Seq[Entreprise]] = driver.query("SELECT * FROM Entreprise")
		
		override def byCodeEntreprise(codeEntreprise: Int): Future[Option[Entreprise]] = driver.query[Seq[Entreprise]](s"SELECT * FROM Entreprise WHERE codeEntreprise=$codeEntreprise").map(_.headOption)
	}
	
	val FormationCollection = new FormationCollection {
		override def all: Future[Seq[Formation]] = driver.query("SELECT * FROM Formation")
		
		override def byCodeFormation(codeFormation: String): Future[Option[Formation]] = driver.query[Seq[Formation]](s"SELECT * FROM Formation WHERE codeFormation='$codeFormation'").map(_.headOption)
	}
	
	val LieuCollection = new LieuCollection {
		override def all: Future[Seq[Lieu]] = driver.query("SELECT * FROM Lieu")
		
		override def byCodeLieu(codeLieu: Int): Future[Option[Lieu]] = driver.query[Seq[Lieu]](s"SELECT * FROM Lieu WHERE codeLieu=$codeLieu").map(_.headOption)
	}
	
	val PromotionCollection = new PromotionCollection {
		override def all: Future[Seq[Promotion]] = driver.query("SELECT * FROM Promotion")
		
		override def byCodePromotion(codePromotion: String): Future[Option[Promotion]] = driver.query[Seq[Promotion]](s"SELECT * FROM Promotion WHERE codePromotion='$codePromotion'").map(_.headOption)
	}
	
	val StagiaireCollection = new StagiaireCollection {
		override def all: Future[Seq[Stagiaire]] = driver.query("SELECT * FROM Stagiaire")
		
		override def byCodeStagiaire(codeStagiaire: Int): Future[Option[Stagiaire]] = driver.query[Seq[Stagiaire]](s"SELECT * FROM Stagiaire WHERE codeStagiaire=$codeStagiaire").map(_.headOption)
	}
	
	val TitreCollection = new TitreCollection {
		override def all: Future[Seq[Titre]] = driver.query("SELECT * FROM Titre")
		
		override def byCodeTitre(codeTitre: String): Future[Option[Titre]] = driver.query[Seq[Titre]](s"SELECT * FROM Titre WHERE codeTitre='$codeTitre'").map(_.headOption)
	}
	
	val PlanningIndividuelFormationCollection = new PlanningIndividuelFormationCollection {
		override def all: Future[Seq[PlanningIndividuelFormation]] = driver.query("SELECT * FROM PlanningIndividuelFormation")
		
		override def byCodePlanning(codePlanning: Int): Future[Option[PlanningIndividuelFormation]] = driver.query[Seq[PlanningIndividuelFormation]](s"SELECT * FROM PlanningIndividuelFormation WHERE codePlanning=$codePlanning").map(_.headOption)
	}
	
	val StagiaireParEntrepriseCollection = new StagiaireParEntrepriseCollection {
		override def all: Future[Seq[StagiaireParEntreprise]] = driver.query("SELECT * FROM StagiaireParEntreprise")
		
		override def byCodeStagiaire(codeStagiaire: Int): Future[Seq[StagiaireParEntreprise]] = driver.query[Seq[StagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE codeStagiaire=$codeStagiaire")
		
		override def byCodeEntreprise(codeEntreprise: Int): Future[Seq[StagiaireParEntreprise]] = driver.query[Seq[StagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE codeEntreprise=$codeEntreprise")
		
		override def byTitreVise(titreVise: String): Future[Seq[StagiaireParEntreprise]] = driver.query[Seq[StagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE titreVise='$titreVise'")
		
		override def byCodeStagiaireAndCodeEntreprise(codeStagiaire: Int, codeEntreprise: Int): Future[Option[StagiaireParEntreprise]] = driver.query[Seq[StagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE codeStagiaire=$codeStagiaire AND codeEntreprise=$codeEntreprise").map(_.headOption)
	}
	
	val UniteParFormationCollection = new UniteParFormationCollection {
		override def all: Future[Seq[UniteParFormation]] = driver.query("SELECT * FROM UniteParFormation")
		
		override def byCodeFormation(codeFormation: String): Future[Seq[UniteParFormation]] = driver.query(s"SELECT * FROM UniteParFormation WHERE codeFromation='$codeFormation'")
		
		override def byId(id: Int): Future[Seq[UniteParFormation]] = driver.query(s"SELECT * FROM UniteParFormation WHERE id=$id")
		
		override def byIdUniteFormation(idUniteFormation: Int): Future[Seq[UniteParFormation]] = driver.query(s"SELECT * FROM UniteParFormation WHERE idUniteFormation=$idUniteFormation")
	}
}
