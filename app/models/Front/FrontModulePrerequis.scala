package models.Front

import play.api.libs.json.{Json, OFormat}

object FrontModulePrerequis {
	implicit val format: OFormat[FrontModulePrerequis] = Json.format[FrontModulePrerequis]
}

case class FrontModulePrerequis(
	                               idModulePrerequis: String,
	                               idModule: Int,
	                               idModuleObligatoire: Seq[Int] = Seq.empty,
	                               idModuleOptionnel: Seq[Int] = Seq.empty,
	                               titre: String,
	                               description: String,
	                               codeFormation: String
                               )