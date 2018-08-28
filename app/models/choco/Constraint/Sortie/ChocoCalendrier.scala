package models.choco.Constraint.Sortie

import models.choco.ChocoPeriod
import play.api.libs.json.{Json, OFormat}

object ChocoCalendrier {
	implicit val format: OFormat[ChocoCalendrier] = Json.format[ChocoCalendrier]
}

case class ChocoCalendrier(
	                          periodOfTraining: Option[ChocoPeriod] = None,
	                          constraints: Seq[ChocoConstraint] = Seq.empty,
	                          cours: Seq[ChocoCours] = Seq.empty
                          )