package models

import play.api.libs.json.Json

object Problem {
	implicit val format = Json.format[Problem]
}

case class Problem (
                   idProblem: String,
                   idConstraint: Option[String] = None,
                   idModuleFormation: Option[String] = None
                   )