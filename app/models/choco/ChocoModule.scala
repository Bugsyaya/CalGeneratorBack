package models.choco

import play.api.libs.json.Json

object ChocoModule {
	implicit val format = Json.format[ChocoModule]
}

case class ChocoModule(
	                      idModule: Int,
	                      nbWeekOfModule: Int,
	                      nbHourOfModule: Int,
	                      listIdModulePrerequisite: Option[Seq[Int]] = None,
	                      listIdModuleOptional: Option[Seq[Int]] = None,
	                      listClasses: Option[Seq[String]] = None
                        )
