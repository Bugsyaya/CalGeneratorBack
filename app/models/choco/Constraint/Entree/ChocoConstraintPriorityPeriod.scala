package models.choco.Constraint.Entree

import models.choco.ChocoPeriod
import play.api.libs.json.{Json, OFormat}

object ChocoConstraintPriorityPeriod {
	implicit val format: OFormat[ChocoConstraintPriorityPeriod] = Json.format[ChocoConstraintPriorityPeriod]
}

case class ChocoConstraintPriorityPeriod(
	                                        priority: Option[Int] = Some(-1),
	                                        value: ChocoPeriod,
	                                        id: String,
	                                        message: Option[String] = None
                                        )