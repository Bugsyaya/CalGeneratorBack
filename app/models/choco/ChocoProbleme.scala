package models.choco

import play.api.libs.json.Json

object ChocoProbleme {
	implicit val format = Json.format[ChocoProbleme]
}

case class ChocoProbleme(
	                    periodOfTrainning: ChocoPeriod,
	                    numberOfCalendarToFound: Int,
	                    constraints: Option[ChocoConstraint] = None,
	                    moduleOfTraining: Seq[ChocoModule] = Seq.empty
                 )
