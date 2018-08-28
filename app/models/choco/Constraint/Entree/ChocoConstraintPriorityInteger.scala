package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityInteger {
	implicit val format: OFormat[ChocoConstraintPriorityInteger] = Json.format[ChocoConstraintPriorityInteger]
}

case class ChocoConstraintPriorityInteger(
	                                         priority: Option[Int] = Some(-1),
	                                         value: Int,
	                                         id: String,
	                                         message: Option[String] = None
                                         )