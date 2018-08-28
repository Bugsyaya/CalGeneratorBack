package models.ENI

import models.choco.Constraint.Sortie.ChocoConstraint
import play.api.libs.json.{Json, OFormat}

object ENICoursCustom {
	implicit val format: OFormat[ENICoursCustom] = Json.format[ENICoursCustom]
}

case class ENICoursCustom(
	                         debut: String = "",
	                         fin: String = "",
	                         dureeReelleEnHeures: Int = 0,
	                         codePromotion: String = "",
	                         idCours: String = "",
	                         idModule: Int = 0,
	                         libelleCours: String = "",
	                         dureePrevueEnHeures: Int = 0,
	                         dateAdefinir: Boolean = false,
	                         codeLieu: Int = 0,
	                         constraints: Seq[ChocoConstraint] = Seq.empty,
	                         alerteModification: Option[String] = None
                         )