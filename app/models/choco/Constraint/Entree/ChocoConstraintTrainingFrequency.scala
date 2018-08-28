package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintTrainingFrequency {
	implicit val format: OFormat[ChocoConstraintTrainingFrequency] = Json.using[Json.WithDefaultValues].format[ChocoConstraintTrainingFrequency]
}

case class ChocoConstraintTrainingFrequency(
	                                           maxWeekInTraining: Int,
	                                           minWeekInCompany: Int
                                           )