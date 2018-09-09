package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraint {
	implicit val format: OFormat[ChocoConstraint] = Json.format[ChocoConstraint]
}

case class ChocoConstraint(
	                          place: Option[ChocoConstraintPriorityInteger] = None,
	                          annualNumberOfHour: Option[ChocoConstraintPriorityInteger] = None,
	                          maxDurationOfTraining: Option[ChocoConstraintPriorityInteger] = None,
	                          trainingFrequency: Option[ChocoConstraintPriorityTrainingFrequency] = None,
	                          maxStudentInTraining: Option[ChocoConstraintPriorityStudentCompany] = None,
	                          listStudentRequired: Option[Seq[ChocoConstraintPriorityStudent]] = None,
	                          listPeriodeOfTrainingExclusion: Option[Seq[ChocoConstraintPriorityPeriod]] = None,
	                          listPeriodeOfTrainingInclusion: Option[Seq[ChocoConstraintPriorityPeriod]] = None,
	                          prerequisModule: Option[ChocoConstraintPriorityBoolean] = None,
	                          titre: Option[String] = None,
	                          description: Option[String] = None
                          )