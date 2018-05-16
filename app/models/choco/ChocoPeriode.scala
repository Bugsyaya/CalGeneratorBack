package models.choco

import play.api.libs.json.Json

object ChocoPeriode {
	implicit val format = Json.format[ChocoPeriode]
}

case class ChocoPeriode (
	                        debut: String,
	                        fin: String,
	                        format: Option[String] = None
                    )