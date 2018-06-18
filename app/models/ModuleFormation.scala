package models

import models.choco.ChocoModule
import play.api.libs.json.Json

object ModuleFormation {
	implicit val format = Json.format[ModuleFormation]
}

case class ModuleFormation(
	                      idModuleFormation: String,
	                      codeFormation: String,
	                      titre: String,
	                      description: Option[String] = None,
	                      chocoModule: Seq[Int] = Seq.empty
                      )