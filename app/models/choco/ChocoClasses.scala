package models.choco

import play.api.libs.json.Json

object ChocoClasses {
	implicit val format = Json.format[ChocoClasses]
}

case class ChocoClasses(
	                       idClasses: String,
	                       period: ChocoPeriod,
	                       realDuration: Int,
	                       idPlace: Int
                      )