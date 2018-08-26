package models.choco.Constraint.Entree

import play.api.libs.json.Json

object ChocoConstraintTrainingFrequency {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintTrainingFrequency]
}

case class ChocoConstraintTrainingFrequency(
	                            maxWeekInTraining: Int,
	                            minWeekInCompany: Int
                            )