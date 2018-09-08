package models.choco.Constraint.Entree

import java.util.UUID

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityStudent {
	implicit val format: OFormat[ChocoConstraintPriorityStudent] = Json.format[ChocoConstraintPriorityStudent]
}

case class ChocoConstraintPriorityStudent(
	                                         priority: Option[Int] = Some(-1),
	                                         value: ChocoConstraintStudent,
	                                         id: Option[String] = Some(UUID.randomUUID().toString),
	                                         message: Option[String] = None
                                         )