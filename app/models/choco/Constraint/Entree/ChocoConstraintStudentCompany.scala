package models.choco.Constraint.Entree

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintStudentCompany {
	implicit val format: OFormat[ChocoConstraintStudentCompany] = Json.using[Json.WithDefaultValues].format[ChocoConstraintStudentCompany]
}

case class ChocoConstraintStudentCompany(
	                                        maxStudentInTraining: Int,
	                                        listStudentCompany: Seq[ChocoConstraintStudent]
                                        )