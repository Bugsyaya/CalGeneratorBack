package models

import models.ENI.ENICours
import models.choco.ChocoConstraint
import play.api.libs.json.Json

object Calendrier {
	implicit val format = Json.format[Calendrier]
}

case class Calendrier (
	                      idCalendrier: String,
	                      codeFormation: String,
	                      cours: Seq[ENICours] = Seq.empty,
	                      contraintesResolus: Seq[ChocoConstraint] = Seq.empty,
	                      contrainteNonResolu: Seq[ChocoConstraint] = Seq.empty
                      )