package models.choco

import play.api.libs.json.{Json, OFormat}

object ChocoPeriod {
	implicit val format: OFormat[ChocoPeriod] = Json.format[ChocoPeriod]
}

case class ChocoPeriod(
	                      start: String,
	                      end: String
                      )