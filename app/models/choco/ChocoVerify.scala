package models.choco

import models.database.Constraint
import play.api.libs.json.{Json, OFormat}

object ChocoVerify {
	implicit val format: OFormat[ChocoVerify] = Json.format[ChocoVerify]
}

case class ChocoVerify(
	                      periodOfTraining: Option[ChocoPeriod] = None,
	                      constraints: Option[Constraint] = None,
	                      moduleOfTraining: Seq[ChocoModule] = Seq.empty
                      )
