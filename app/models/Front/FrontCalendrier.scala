package models.Front

import models.choco.{ChocoConstraint, ChocoPeriod}
import play.api.libs.json.Json

object FrontCalendrier {
	implicit val format = Json.format[FrontCalendrier]
}

case class FrontCalendrier (
	                           periodOfTraining: ChocoPeriod,
	                           numberOfCalendarToFound: Option[Int] = Some(5),
	                           idProblem: String,
	                           codeFormation: String
                           )