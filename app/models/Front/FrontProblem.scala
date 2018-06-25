package models.Front

import models.choco.ChocoPeriod
import play.api.libs.json.Json

object FrontProblem {
	implicit val format = Json.format[FrontProblem]
}

case class FrontProblem(
	                       periodOfTrainning: ChocoPeriod,
	                       numberOfCalendarToFound: Int = 5,
	                       idProblem: Option[String] = None,
	                       idConstraint: Option[String] = None,
	                       idModuleFormation: Option[String] = None
                       )