package models

import models.choco.ChocoModule
import play.api.libs.json.Json

object ModuleFormation {
	implicit val format = Json.format[ModuleFormation]
}

case class ModuleFormation(
	                      idModuleFormation: Option[String] = None,
	                      codeFormation: String,
	                      titre: Option[String] = None,
	                      description: Option[String] = None,
	                      chocoModule: Option[Seq[Int]] = None
                      )