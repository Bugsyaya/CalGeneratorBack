package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityStudent {
	implicit val format: OFormat[ChocoConstraintPriorityStudent] = Json.format[ChocoConstraintPriorityStudent]
}

case class ChocoConstraintPriorityStudent(
	                                         priority: Option[Int] = Some(-1),
	                                         value: ChocoConstraintStudent,
	                                         id: String,
	                                         message: Option[String] = None
                                         )