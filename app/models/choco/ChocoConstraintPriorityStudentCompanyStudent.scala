package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityStudentCompanyStudent {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintPriorityStudentCompanyStudent]
}

case class ChocoConstraintPriorityStudentCompanyStudent(
	                             maxStudentInTraining: Int,
	                             listStudentCompany: ChocoConstraintPriorityStudentCompanyStudent
                             )