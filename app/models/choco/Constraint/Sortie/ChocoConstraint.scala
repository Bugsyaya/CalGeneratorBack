package models.choco.Constraint.Sortie

import play.api.libs.json.{Json, OFormat}

object ChocoConstraint {
	implicit val format: OFormat[ChocoConstraint] = Json.format[ChocoConstraint]
}

case class ChocoConstraint(
	                          name: String,
	                          id: String
                          )
