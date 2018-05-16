package models.choco

import play.api.libs.json.Json

object ChocoContrainte {
	implicit val format = Json.format[ChocoContrainte]
}

case class ChocoContrainte (
	                           idLieux: Seq[Int] = Nil,
	                           nbHeureAnnuel: Option[Int] = None,
	                           dureeMaxFormation: Option[Int] = None,
	                           maxSemaineFormation: Option[Int] = None,
	                           periodeFormationExclusion: Seq[ChocoPeriode] = Nil,
	                           periodeFormationInclusion: Seq[ChocoPeriode] = Nil,
	                           maxStagiaireEntrepriseEnFormation: Option[Int] = None,
	                           stagiairesEntreprise: Seq[ChocoStagiaire] = Nil,
	                           stagiairesRecquis: Seq[ChocoStagiaire] = Nil
                           )