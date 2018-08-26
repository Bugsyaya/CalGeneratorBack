package models.Front

import play.api.libs.json.Json

object FrontModulePrerequisPlanning {
	implicit val format = Json.format[FrontModulePrerequisPlanning]
}

case class FrontModulePrerequisPlanning (
                                        idModulePrerequisPlanning: String,
                                        idModulePrerequis: Seq[String],
                                        titre: String,
                                        description: String
                                        )