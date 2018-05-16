package models

import models.choco.{ChocoContrainte, ChocoCours}
import play.api.libs.json.Json

object Calendrier {
	implicit val format = Json.format[Calendrier]
}

case class Calendrier (
	                           cours: Seq[Cours],
	                           contraintesResolus: Seq[ChocoContrainte],
	                           contrainteNonResolu: Seq[ChocoContrainte]
                           )