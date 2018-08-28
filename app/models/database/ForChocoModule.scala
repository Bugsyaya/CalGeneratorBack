package models.database

import play.api.libs.json.{Json, OFormat}

object ForChocoModule {
	implicit val format: OFormat[ForChocoModule] = Json.format[ForChocoModule]
}

case class ForChocoModule(
	                         idForChocoModule: String,
	                         idModuleAndCodeFormation: Seq[(Int, String)]
                         )
