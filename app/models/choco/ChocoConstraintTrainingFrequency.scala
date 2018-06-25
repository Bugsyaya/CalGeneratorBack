package models.choco

import play.api.libs.json.Json

object ChocoConstraintTrainingFrequency {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintTrainingFrequency]
}

case class ChocoConstraintTrainingFrequency(
	                            maxWeekInTraining: Int,
	                            minWeekInCompagny: Int
                            )