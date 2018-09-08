package models.choco.Constraint.Entree

import java.util.UUID

import models.choco.ChocoPeriod
import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityPeriod {
	implicit val format: OFormat[ChocoConstraintPriorityPeriod] = Json.format[ChocoConstraintPriorityPeriod]
}

case class ChocoConstraintPriorityPeriod(
	                                        priority: Option[Int] = Some(-1),
	                                        value: ChocoPeriod,
	                                        id: Option[String] = Some(UUID.randomUUID().toString),
	                                        message: Option[String] = None
                                        )