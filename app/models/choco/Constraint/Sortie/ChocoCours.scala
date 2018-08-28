package models.choco.Constraint.Sortie

import play.api.libs.json.{Json, OFormat}

object ChocoCours {
	implicit val format: OFormat[ChocoCours] = Json.format[ChocoCours]
}

case class ChocoCours(
	                     idModule: Int,
	                     idClasses: String,
	                     constraints: Seq[ChocoConstraint] = Seq.empty
                     )
