package models.choco.Constraint.Sortie

import play.api.libs.json.Json

object ChocoCours {
	implicit val format = Json.format[ChocoCours]
}

case class ChocoCours (
	idModule: Int,
	idClasses: String,
	constraints: Seq[ChocoConstraint]
                 )
