package models

import models.ENI.ENICours
import models.choco.Constraint.Sortie.{ChocoConstraint, ChocoCours}
import play.api.libs.json.Json

object Calendrier {
	implicit val format = Json.format[Calendrier]
}

case class Calendrier (
	                      idCalendrier: String,
	                      constraint: Seq[ChocoConstraint],
	                      cours: Seq[ENICours] = Seq.empty
                      )