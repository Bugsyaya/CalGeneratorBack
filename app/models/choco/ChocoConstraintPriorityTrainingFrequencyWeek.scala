package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityTrainingFrequencyWeek {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintPriorityTrainingFrequencyWeek]
}

case class ChocoConstraintPriorityTrainingFrequencyWeek(
	                            maxWeekInTraining: Int,
	                            minWeekInCompagny: Int
                            )