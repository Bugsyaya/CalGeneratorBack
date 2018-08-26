package models.choco.Constraint.Sortie

import play.api.libs.json.Json

object ChocoCalendrier {
	implicit val format = Json.format[ChocoCalendrier]
}

case class ChocoCalendrier (
//                           id : Option[String] = None,
	                           constraints: Seq[ChocoConstraint],
	                           cours: Seq[ChocoCours]
                           )