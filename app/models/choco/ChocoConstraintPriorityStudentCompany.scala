package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityStudentCompany {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintPriorityStudentCompany]
}

case class ChocoConstraintPriorityStudentCompany(
	                                                priority: Option[Int] = Some(-1),
	                               value: ChocoConstraintPriorityStudentCompanyStudent
                               )