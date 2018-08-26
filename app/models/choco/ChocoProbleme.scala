package models.choco

import models.choco.Constraint.Entree.ChocoConstraint
import play.api.libs.json.Json

object ChocoProbleme {
	implicit val format = Json.format[ChocoProbleme]
}

case class ChocoProbleme(
	                        periodOfTraining: ChocoPeriod,
	                        numberOfCalendarToFound: Int,
	                        constraints: Option[ChocoConstraint] = None,
	                        moduleOfTraining: Seq[ChocoModule] = Seq.empty
                 )
