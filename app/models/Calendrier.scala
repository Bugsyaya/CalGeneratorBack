package models

import models.choco.ChocoContrainte
import play.api.libs.json.Json

object Calendrier {
	implicit val format = Json.format[Calendrier]
}

case class Calendrier (
	                      idCalendrier: String,
	                      codeFormation: String,
	                      cours: Seq[Cours] = Seq.empty,
	                      contraintesResolus: Seq[ChocoContrainte] = Seq.empty,
	                      contrainteNonResolu: Seq[ChocoContrainte] = Seq.empty
                      )