package models.choco.Constraint.Entree

import java.util.UUID

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityInteger {
	implicit val format: OFormat[ChocoConstraintPriorityInteger] = Json.format[ChocoConstraintPriorityInteger]
}

case class ChocoConstraintPriorityInteger(
	                                         priority: Option[Int] = Some(-1),
	                                         value: Int,
	                                         id: Option[String] = Some(UUID.randomUUID().toString),
	                                         message: Option[String] = None
                                         )