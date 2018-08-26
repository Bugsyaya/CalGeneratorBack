package models.Front

import models.choco.Constraint.Entree._
import models.choco._
import play.api.libs.json.Json

object FrontContraint {
	implicit val format = Json.format[FrontContraint]
}

case class FrontContraint(
                         id: String,
	                          place: Option[ChocoConstraintPriorityInteger] = None,
	                          annualNumberOfHour: Option[ChocoConstraintPriorityInteger] = None,
	                          maxDurationOfTraining: Option[ChocoConstraintPriorityInteger] = None,
	                          trainingFrequency: Option[ChocoConstraintPriorityTrainingFrequency] = None,
	                          maxStudentInTraining: Option[ChocoConstraintPriorityStudentCompany] = None,
	                          listStudentRequired: Option[Seq[ChocoConstraintPriorityStudent]] = None,
	                          listPeriodeOfTrainingExclusion: Option[Seq[ChocoConstraintPriorityPeriod]] = None,
	                          listPeriodeOfTrainingInclusion: Option[Seq[ChocoConstraintPriorityPeriod]] = None,
	                          prerequisModule: Option[ChocoConstraintPriorityBoolean] = None
                          )