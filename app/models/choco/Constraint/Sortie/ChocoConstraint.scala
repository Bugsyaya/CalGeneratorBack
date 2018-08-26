package models.choco.Constraint.Sortie

import play.api.libs.json.Json

object ChocoConstraint {
	implicit val format = Json.format[ChocoConstraint]
}

case class ChocoConstraint (
	name: String,
	id: String
                           )
