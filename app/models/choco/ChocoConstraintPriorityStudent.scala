package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityStudent {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintPriorityStudent]
}

case class ChocoConstraintPriorityStudent(
	                                         priority: Option[Int] = Some(-1),
                                         idStudent: Int,
                                         listClassees: Seq[ChocoClasses]
                                         )