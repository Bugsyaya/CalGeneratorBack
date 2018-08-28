package database

import models.ENI._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ENIDB(driver: DBDriverENI) extends API {
	val UniteFormationCollection = new UniteFormationCollection {
		override def all: Future[Seq[ENIUniteFormation]] = driver.query("SELECT * FROM UniteFormation")
		
		override def byId(idUniteFormation: Int): Future[Option[ENIUniteFormation]] = driver.query[Seq[ENIUniteFormation]](s"SELECT * FROM UniteFormation WHERE idUniteFormation=$idUniteFormation").map(_.headOption)
	}
	
	val ModuleCollection = new ModuleCollection {
		override def all: Future[Seq[ENIModule]] = driver.query("SELECT * FROM Module")
		
		override def byId(idModule: Int): Future[Option[ENIModule]] = driver.query[Seq[ENIModule]](s"SELECT * FROM Module WHERE idModule=$idModule").map(_.headOption)
		
		override def byDateAndFormation(debut: String, fin: String, codeFormation: String): Future[Seq[Int]] = {
			driver.queryBasic(
				s"""SELECT c.idModule
					  FROM cours c
					  LEFT JOIN module m ON c.IdModule = m.IdModule
					  LEFT JOIN ModuleParUnite mpu ON m.IdModule = mpu.IdModule
					  LEFT JOIN UniteParFormation upf ON mpu.IdUnite = upf.Id
					  LEFT JOIN Formation f ON f.CodeFormation = upf.CodeFormation
					  WHERE (c.Debut > '$debut' AND c.Fin < '$fin') AND f.CodeFormation ='$codeFormation'
					  GROUP BY c.idModule""", "idModule").map(_.map(_.toInt))
		}
	}
	
	val ModuleParUniteCollection = new ModuleParUniteCollection {
		override def all: Future[Seq[ENIModuleParUnite]] = driver.query("SELECT * FROM ModuleParUnite")
		
		override def byId(id: Int): Future[Option[ENIModuleParUnite]] = driver.query[Seq[ENIModuleParUnite]](s"SELECT * FROM ModuleParUnite WHERE id=$id").map(_.headOption)
		
		override def byIdModule(idModule: Int): Future[Seq[ENIModuleParUnite]] = driver.query[Seq[ENIModuleParUnite]](s"SELECT * FROM ModuleParUnite WHERE idModule=$idModule")
		
		override def byIdUnite(idUnite: Int): Future[Seq[ENIModuleParUnite]] = driver.query[Seq[ENIModuleParUnite]](s"SELECT * FROM ModuleParUnite WHERE idUnite=$idUnite")
	}
	
	val SalleCollection = new SalleCollection {
		override def all: Future[Seq[ENISalle]] = driver.query("SELECT * FROM Salle")
		
		override def byCodeSalle(codeSalle: String): Future[Option[ENISalle]] = driver.query[Seq[ENISalle]](s"SELECT * FROM Salle WHERE codeSalle='$codeSalle'").map(_.headOption)
	}
	
	val CoursCollection = new CoursCollection {
		override def all: Future[Seq[ENICours]] = driver.query("SELECT * FROM Cours")
		
		override def byId(id: String): Future[Option[ENICours]] = driver.query[Seq[ENICours]](s"SELECT * FROM Cours WHERE idCours='$id'").map(_.headOption)
		
		override def byDateAndModule(debut: String, fin: String, idModule: Int): Future[Seq[String]] = {
			driver.queryBasic(
				s"""SELECT c.idCours FROM cours c
				left join module m on c.IdModule = m.IdModule
				left join ModuleParUnite mpu on m.IdModule = mpu.IdModule
				left join UniteParFormation upf on mpu.IdUnite = upf.Id
				left join Formation f on f.CodeFormation = upf.CodeFormation
				where (c.Debut > '$debut' AND c.Fin < '$fin') AND c.idModule=$idModule
				GROUP BY c.idCours""", "IdCours")
		}
		
		override def byModule(idModule: Int): Future[Seq[String]] = {
			driver.queryBasic(s"""SELECT idCours FROM cours WHERE IdModule = $idModule""", "idCours")
		}
	}
	
	val EntrepriseCollection = new EntrepriseCollection {
		override def all: Future[Seq[ENIEntreprise]] = driver.query("SELECT * FROM Entreprise")
		
		override def byCodeEntreprise(codeEntreprise: Int): Future[Option[ENIEntreprise]] = driver.query[Seq[ENIEntreprise]](s"SELECT * FROM Entreprise WHERE codeEntreprise=$codeEntreprise").map(_.headOption)
	}
	
	val FormationCollection = new FormationCollection {
		override def all: Future[Seq[ENIFormation]] = driver.query("SELECT * FROM Formation")
		
		override def byCodeFormation(codeFormation: String): Future[Option[ENIFormation]] = driver.query[Seq[ENIFormation]](s"SELECT * FROM Formation WHERE codeFormation='$codeFormation'").map(_.headOption)
		
		override def moduleByCodeFormation(codeFormation: String): Future[Seq[String]] = driver.queryBasic(
			s"""SELECT m.IdModule FROM cours c
			   left join module m on c.IdModule = m.IdModule
			   left join ModuleParUnite mpu on m.IdModule = mpu.IdModule
			   left join UniteParFormation upf on mpu.IdUnite = upf.Id
			   left join Formation f on f.CodeFormation = upf.CodeFormation
			   where f.CodeFormation = '$codeFormation'
			   GROUP BY m.IdModule
			 """, "IdModule")
	}
	
	val LieuCollection = new LieuCollection {
		override def all: Future[Seq[ENILieu]] = driver.query("SELECT * FROM Lieu")
		
		override def byCodeLieu(codeLieu: Int): Future[Option[ENILieu]] = driver.query[Seq[ENILieu]](s"SELECT * FROM Lieu WHERE codeLieu=$codeLieu").map(_.headOption)
	}
	
	val PromotionCollection = new PromotionCollection {
		override def all: Future[Seq[ENIPromotion]] = driver.query("SELECT * FROM Promotion")
		
		override def byCodePromotion(codePromotion: String): Future[Option[ENIPromotion]] = driver.query[Seq[ENIPromotion]](s"SELECT * FROM Promotion WHERE codePromotion='$codePromotion'").map(_.headOption)
	}
	
	val StagiaireCollection = new StagiaireCollection {
		override def all: Future[Seq[ENIStagiaire]] = driver.query("SELECT * FROM Stagiaire")
		
		override def byCodeStagiaire(codeStagiaire: Int): Future[Option[ENIStagiaire]] = driver.query[Seq[ENIStagiaire]](s"SELECT * FROM Stagiaire WHERE codeStagiaire=$codeStagiaire").map(_.headOption)
	}
	
	val TitreCollection = new TitreCollection {
		override def all: Future[Seq[ENITitre]] = driver.query("SELECT * FROM Titre")
		
		override def byCodeTitre(codeTitre: String): Future[Option[ENITitre]] = driver.query[Seq[ENITitre]](s"SELECT * FROM Titre WHERE codeTitre='$codeTitre'").map(_.headOption)
	}
	
	val PlanningIndividuelFormationCollection = new PlanningIndividuelFormationCollection {
		override def all: Future[Seq[ENIPlanningIndividuelFormation]] = driver.query("SELECT * FROM PlanningIndividuelFormation")
		
		override def byCodePlanning(codePlanning: Int): Future[Option[ENIPlanningIndividuelFormation]] = driver.query[Seq[ENIPlanningIndividuelFormation]](s"SELECT * FROM PlanningIndividuelFormation WHERE codePlanning=$codePlanning").map(_.headOption)
	}
	
	val StagiaireParEntrepriseCollection = new StagiaireParEntrepriseCollection {
		override def all: Future[Seq[ENIStagiaireParEntreprise]] = driver.query("SELECT * FROM StagiaireParEntreprise")
		
		override def byCodeStagiaire(codeStagiaire: Int): Future[Seq[ENIStagiaireParEntreprise]] = driver.query[Seq[ENIStagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE codeStagiaire=$codeStagiaire")
		
		override def byCodeEntreprise(codeEntreprise: Int): Future[Seq[ENIStagiaireParEntreprise]] = driver.query[Seq[ENIStagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE codeEntreprise=$codeEntreprise")
		
		override def byTitreVise(titreVise: String): Future[Seq[ENIStagiaireParEntreprise]] = driver.query[Seq[ENIStagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE titreVise='$titreVise'")
		
		override def byCodeStagiaireAndCodeEntreprise(codeStagiaire: Int, codeEntreprise: Int): Future[Option[ENIStagiaireParEntreprise]] = driver.query[Seq[ENIStagiaireParEntreprise]](s"SELECT * FROM StagiaireParEntreprise WHERE codeStagiaire=$codeStagiaire AND codeEntreprise=$codeEntreprise").map(_.headOption)
	}
	
	val UniteParFormationCollection = new UniteParFormationCollection {
		override def all: Future[Seq[ENIUniteParFormation]] = driver.query("SELECT * FROM UniteParFormation")
		
		override def byCodeFormation(codeFormation: String): Future[Seq[ENIUniteParFormation]] = driver.query(s"SELECT * FROM UniteParFormation WHERE codeFromation='$codeFormation'")
		
		override def byId(id: Int): Future[Seq[ENIUniteParFormation]] = driver.query(s"SELECT * FROM UniteParFormation WHERE id=$id")
		
		override def byIdUniteFormation(idUniteFormation: Int): Future[Seq[ENIUniteParFormation]] = driver.query(s"SELECT * FROM UniteParFormation WHERE idUniteFormation=$idUniteFormation")
	}
}
