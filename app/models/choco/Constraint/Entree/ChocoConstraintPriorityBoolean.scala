package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityBoolean {
	implicit val format: OFormat[ChocoConstraintPriorityBoolean] = Json.format[ChocoConstraintPriorityBoolean]
}

case class ChocoConstraintPriorityBoolean(
	                                         priority: Option[Int] = Some(-1),
	                                         value: Boolean,
	                                         id: String,
	                                         message: Option[String] = None
                                         )