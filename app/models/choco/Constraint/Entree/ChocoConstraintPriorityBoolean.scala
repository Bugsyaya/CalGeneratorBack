package models.choco.Constraint.Entree

import play.api.libs.json.Json

object ChocoConstraintPriorityBoolean {
	implicit val format = Json.format[ChocoConstraintPriorityBoolean]
}

case class ChocoConstraintPriorityBoolean(
	                                         priority: Option[Int] = Some(-1),
	                            value: Boolean,
	                                         id: String
                            )