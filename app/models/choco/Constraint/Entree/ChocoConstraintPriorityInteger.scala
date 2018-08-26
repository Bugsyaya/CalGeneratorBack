package models.choco.Constraint.Entree

import java.util.UUID

import play.api.libs.json.Json

object ChocoConstraintPriorityInteger {
	implicit val format = Json.format[ChocoConstraintPriorityInteger]
}

case class ChocoConstraintPriorityInteger(
	                                         priority: Option[Int] = Some(-1),
	                                         value: Int,
	                                         id: String
                                         )