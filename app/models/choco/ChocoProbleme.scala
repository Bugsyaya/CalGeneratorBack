package models.choco

import play.api.libs.json.Json

object ChocoProbleme {
	implicit val format = Json.format[ChocoProbleme]
}

case class ChocoProbleme(
	                    periodOfTrainning: Option[ChocoPeriod] = None,
	                    numberOfCalendarToFound: Int = 5,
	                    constraints: Option[ChocoConstraint] = None,
	                    moduleOfTraining: Seq[ChocoModule] = Seq.empty
                 )
