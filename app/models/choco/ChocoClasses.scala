package models.choco

import play.api.libs.json.{Json, OFormat}

object ChocoClasses {
	implicit val format: OFormat[ChocoClasses] = Json.format[ChocoClasses]
}

case class ChocoClasses(
	                       idClasses: String,
	                       period: ChocoPeriod,
	                       realDuration: Int,
	                       idPlace: Int
                       )