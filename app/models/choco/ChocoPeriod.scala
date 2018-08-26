package models.choco

import play.api.libs.json.Json

object ChocoPeriod {
	implicit val format = Json.format[ChocoPeriod]
}

case class ChocoPeriod(
                                        start: String,
                                        end: String
                                        )