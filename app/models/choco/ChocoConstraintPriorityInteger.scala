package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityInteger {
	implicit val format = Json.format[ChocoConstraintPriorityInteger]
}

case class ChocoConstraintPriorityInteger(
	                                         priority: Option[Int] = Some(-1),
	                           value: Int
                           )