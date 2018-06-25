package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityStudent {
	implicit val format = Json.format[ChocoConstraintPriorityStudent]
}

case class ChocoConstraintPriorityStudent(
	                                         priority: Option[Int] = Some(-1),
	                                         value: ChocoConstraintStudent
                                         )