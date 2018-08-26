package models.choco.Constraint.Entree

import play.api.libs.json.Json

object ChocoConstraintPriorityTrainingFrequency {
	implicit val format = Json.format[ChocoConstraintPriorityTrainingFrequency]
}

case class ChocoConstraintPriorityTrainingFrequency(
	                                                   priority: Option[Int] = Some(-1),
	                           value: ChocoConstraintTrainingFrequency,
	                                                   id: String
                           )