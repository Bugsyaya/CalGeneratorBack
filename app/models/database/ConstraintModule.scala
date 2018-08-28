package models.database

import play.api.libs.json.{Json, OFormat}

object ConstraintModule {
	implicit val format: OFormat[ConstraintModule] = Json.format[ConstraintModule]
}

case class ConstraintModule(
	                           idModule: Int,
	                           codeFormation: String,
	                           listIdModulePrerequisite: Option[Seq[Int]] = None,
	                           listIdModuleOptional: Option[Seq[Int]] = None
                           )