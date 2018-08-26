package models.Front

import play.api.libs.json.Json

object FrontModulePrerequis {
	implicit val format = Json.format[FrontModulePrerequis]
}

case class FrontModulePrerequis(
                               idModulePrerequis: String,
                               idModule: Int,
                               idModuleObligatoire: Seq[Int] = Seq.empty,
                               idModuleOpionnel: Seq[Int] = Seq.empty,
                               titre: String,
                               description: String
                               )