package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityTrainingFrequency {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintPriorityTrainingFrequency]
}

case class ChocoConstraintPriorityTrainingFrequency(
	                                                   priority: Option[Int] = Some(-1),
	                           value: ChocoConstraintPriorityTrainingFrequencyWeek
                           )