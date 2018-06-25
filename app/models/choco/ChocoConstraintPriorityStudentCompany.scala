package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityStudentCompany{
	implicit val format = Json.format[ChocoConstraintPriorityStudentCompany]
}

case class ChocoConstraintPriorityStudentCompany(
	                                                priority: Option[Int] = Some(-1),
	                               value: ChocoConstraintStudentCompany
                               )