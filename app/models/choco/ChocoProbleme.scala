package models.choco

import models.choco.Constraint.Entree.ChocoConstraint
import play.api.libs.json.{Json, OFormat}

object ChocoProbleme {
	implicit val format: OFormat[ChocoProbleme] = Json.format[ChocoProbleme]
}

case class ChocoProbleme(
	                        periodOfTraining: ChocoPeriod,
	                        numberOfCalendarToFound: Int,
	                        constraints: Option[ChocoConstraint] = None,
	                        moduleOfTraining: Seq[ChocoModule] = Seq.empty
                        )
