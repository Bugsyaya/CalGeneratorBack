package models.database

import java.util.UUID

import models.choco.Constraint.Entree._
import play.api.libs.json.{Json, OFormat}

object Constraint {
	implicit val format: OFormat[Constraint] = Json.format[Constraint]
}

case class Constraint(
	                     idConstraint: String,
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
