package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityTrainingFrequency {
	implicit val format: OFormat[ChocoConstraintPriorityTrainingFrequency] = Json.format[ChocoConstraintPriorityTrainingFrequency]
}

case class ChocoConstraintPriorityTrainingFrequency(
	                                                   priority: Option[Int] = Some(-1),
	                                                   value: ChocoConstraintTrainingFrequency,
	                                                   id: String,
	                                                   message: Option[String] = None
                                                   )