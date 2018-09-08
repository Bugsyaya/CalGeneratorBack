package models.database

import play.api.libs.json.{Json, OFormat}

object StagiaireCours {
	implicit val format: OFormat[StagiaireCours] = Json.format[StagiaireCours]
}

case class StagiaireCours(
	                         idCours: String,
	                         nbStagiaire: Int
                         )
