package models.choco

import play.api.libs.json.Json

object ChocoConstraint {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraint]
}

case class ChocoConstraint(
	                          idConstraint: Option[String] = None,
	                          place: Option[ChocoConstraintPriorityInteger] = None,
	                          annualNumberOfHour: Option[ChocoConstraintPriorityInteger] = None,
	                          maxDurationOfTraining: Option[ChocoConstraintPriorityInteger] = None,
	                          trainingFrequency: Option[ChocoConstraintPriorityTrainingFrequency] = None,
	                          maxStudentInTraining: Option[ChocoConstraintPriorityStudentCompany] = None,
	                          listStudentRequired: Seq[ChocoConstraintPriorityStudent] = Seq.empty,
	                          listPeriodeOfTrainingExclusion: Seq[ChocoConstraintPriorityPeriod] = Seq.empty,
	                          listPeriodeOfTrainingInclusion: Seq[ChocoConstraintPriorityPeriod] = Seq.empty//,
//	                          prerequisModule: Option[ChocoConstraintPriorityBoolean] = None
                           )