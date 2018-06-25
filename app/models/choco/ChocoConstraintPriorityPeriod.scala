package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityPeriod {
	implicit val format = Json.format[ChocoConstraintPriorityPeriod]
}

case class ChocoConstraintPriorityPeriod(
	                                        priority: Option[Int] = Some(-1),
	                             value: ChocoPeriod
                             )