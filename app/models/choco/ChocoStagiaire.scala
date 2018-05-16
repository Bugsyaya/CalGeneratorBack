package models.choco

import play.api.libs.json.Json

object ChocoStagiaire {
	implicit val format = Json.format[ChocoStagiaire]
}

case class ChocoStagiaire (
	                      cours: Seq[ChocoCours]
                      )