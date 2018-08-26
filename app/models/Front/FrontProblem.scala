package models.Front

import models.choco.ChocoPeriod
import play.api.libs.json.Json

object FrontProblem {
	implicit val format = Json.format[FrontProblem]
}

case class FrontProblem(
	                       periodOfTraining: ChocoPeriod,
	                       numberOfCalendarToFound: Int = 5,
	                       idConstraint: Option[String] = None,
	                       idModulePrerequisPlanning: Option[String] = None,
	                       codeFormation: String
                       )