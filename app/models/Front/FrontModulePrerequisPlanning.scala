package models.Front

import play.api.libs.json.{Json, OFormat}

object FrontModulePrerequisPlanning {
	implicit val format: OFormat[FrontModulePrerequisPlanning] = Json.format[FrontModulePrerequisPlanning]
}

case class FrontModulePrerequisPlanning(
	                                       idModulePrerequisPlanning: String,
	                                       idModulePrerequis: Seq[String] = Seq.empty,
	                                       titre: String,
	                                       description: String
                                       )