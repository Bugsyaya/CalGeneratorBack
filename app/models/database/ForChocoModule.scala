package models.database

import play.api.libs.json.Json

object ForChocoModule {
	implicit val format = Json.format[ForChocoModule]
}

case class ForChocoModule(
	                         idForChocoModule: String,
	                         idModuleAndCodeFormation: Seq[(Int, String)]
                         )
