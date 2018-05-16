package models.choco

import play.api.libs.json.Json

object ChocoCalendrier {
	implicit val format = Json.format[ChocoCalendrier]
}

case class ChocoCalendrier (
	                           cours: Seq[ChocoCours],
	                           contraintesResolus: Seq[ChocoContrainte],
	                           contrainteNonResolu: Seq[ChocoContrainte]
                           )