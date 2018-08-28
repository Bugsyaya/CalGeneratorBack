package models.choco

import play.api.libs.json.{Json, OFormat}

object ChocoModule {
	implicit val format: OFormat[ChocoModule] = Json.format[ChocoModule]
}

case class ChocoModule(
	                      idModule: Int,
	                      nbWeekOfModule: Int,
	                      nbHourOfModule: Int,
	                      listIdModulePrerequisite: Seq[Int],
	                      listIdModuleOptional: Seq[Int],
	                      listClasses: Seq[ChocoClasses]
                      )
