package models.choco.Constraint.Entree

import play.api.libs.json.Json

object ChocoConstraintStudentCompany {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintStudentCompany]
}

case class ChocoConstraintStudentCompany(
	                             maxStudentInTraining: Int,
	                             listStudentCompany: Seq[ChocoConstraintStudent]
                             )