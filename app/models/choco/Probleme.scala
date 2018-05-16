package models.choco

import play.api.libs.json.Json

object Probleme {
	implicit val format = Json.format[Probleme]
}

case class Probleme (
	                 periode: ChocoPeriode,
	                 modulesFormation: Seq[ChocoModulesFormation],
	                 contraintes: Seq[ChocoContrainte]
                 )