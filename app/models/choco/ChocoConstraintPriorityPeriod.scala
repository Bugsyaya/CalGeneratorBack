package models.choco

import play.api.libs.json.Json

object ChocoConstraintPriorityPeriod {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintPriorityPeriod]
}

case class ChocoConstraintPriorityPeriod(
	                             priority: Int = -1,
	                             value: ChocoPeriod
                             )