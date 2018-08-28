package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityStudentCompany {
	implicit val format: OFormat[ChocoConstraintPriorityStudentCompany] = Json.format[ChocoConstraintPriorityStudentCompany]
}

case class ChocoConstraintPriorityStudentCompany(
	                                                priority: Option[Int] = Some(-1),
	                                                value: ChocoConstraintStudentCompany,
	                                                id: String,
	                                                message: Option[String] = None
                                                )