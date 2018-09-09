package models.choco.Constraint.Entree

import java.util.UUID

import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityBoolean {
	implicit val format: OFormat[ChocoConstraintPriorityBoolean] = Json.format[ChocoConstraintPriorityBoolean]
}

case class ChocoConstraintPriorityBoolean(
	                                         priority: Option[Int] = Some(-1),
	                                         value: Boolean,
	                                         id: Option[String] = Some(UUID.randomUUID().toString),
	                                         message: Option[String] = None
                                         )