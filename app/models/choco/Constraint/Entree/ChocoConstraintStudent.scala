package models.choco.Constraint.Entree

import models.choco.ChocoClasses
import play.api.libs.json.Json

object ChocoConstraintStudent{
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoConstraintStudent]
}

case class ChocoConstraintStudent(
	                                 idStudent: Int,
	                                 listClassees: Seq[ChocoClasses]
                                 )