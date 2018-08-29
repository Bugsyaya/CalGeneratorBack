package models

import models.ENI.ENICoursCustom
import models.choco.ChocoPeriod
import models.database.Constraint
import play.api.libs.json.{Json, OFormat}

object Calendrier {
	implicit val format: OFormat[Calendrier] = Json.format[Calendrier]
}

case class Calendrier(
	                     idCalendrier: String,
	                     status: Option[String],
	                     periodOfTraining: Option[ChocoPeriod] = None,
	                     constraint: Option[Constraint] = None,
	                     cours: Seq[ENICoursCustom] = Seq.empty,
	                     idModulePrerequisPlanning: Option[String],
	                     titre: Option[String] = None,
	                     description: Option[String] = None
                     )