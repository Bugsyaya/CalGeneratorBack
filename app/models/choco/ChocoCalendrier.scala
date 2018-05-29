package models.choco

import play.api.libs.json.Json

object ChocoCalendrier {
	implicit val format = Json.format[ChocoCalendrier]
}

case class ChocoCalendrier (
	                           contraintesResolus: Seq[ChocoContrainte] = Nil,
	                           contrainteNonResolu: Seq[ChocoContrainte] = Nil,
	                           cours: Seq[String]
                           )