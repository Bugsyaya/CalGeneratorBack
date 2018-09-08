package models.choco.Constraint.Entree

import java.util.UUID

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityTrainingFrequency {
	implicit val format: OFormat[ChocoConstraintPriorityTrainingFrequency] = Json.format[ChocoConstraintPriorityTrainingFrequency]
}

case class ChocoConstraintPriorityTrainingFrequency(
	                                                   priority: Option[Int] = Some(-1),
	                                                   value: ChocoConstraintTrainingFrequency,
	                                                   id: Option[String] = Some(UUID.randomUUID().toString),
	                                                   message: Option[String] = None
                                                   )