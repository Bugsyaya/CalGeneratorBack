package models.choco

import play.api.libs.json.Json

object ChocoModulesFormation {
	implicit val format = Json.format[ChocoModulesFormation]
}

case class ChocoModulesFormation (
	                                 idModule: String,
	                                 prerequis: Seq[ChocoModulesFormation],
	                                 cours: Seq[ChocoCours],
	                                 nbSemainePrevu: Int,
	                                 nbHeurePrevu: Int
                        )
