package models.choco

import play.api.libs.json.Json

object ChocoCalendrier {
	implicit val format = Json.format[ChocoCalendrier]
}

case class ChocoCalendrier (
	                           contraintesResolus: Seq[ChocoConstraint] = Nil,
	                           contrainteNonResolu: Seq[ChocoConstraint] = Nil,
	                           cours: Seq[String]
                           )