package models.choco.Constraint.Entree

import java.util.UUID

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityStudentCompany {
	implicit val format: OFormat[ChocoConstraintPriorityStudentCompany] = Json.format[ChocoConstraintPriorityStudentCompany]
}

case class ChocoConstraintPriorityStudentCompany(
	                                                priority: Option[Int] = Some(-1),
	                                                value: Seq[ChocoConstraintStudentCompany] = Seq.empty,
	                                                id: Option[String] = Some(UUID.randomUUID().toString),
	                                                message: Option[String] = None
                                                )