package models

import play.api.libs.json.{Json, OFormat}

object ModuleFormation {
	implicit val format: OFormat[ModuleFormation] = Json.format[ModuleFormation]
}

case class ModuleFormation(
	                          idModuleFormation: Option[String] = None,
	                          codeFormation: String,
	                          titre: Option[String] = None,
	                          description: Option[String] = None,
	                          chocoModule: Option[Seq[Int]] = None
                          )