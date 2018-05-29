package models.choco

import play.api.libs.json.Json

object ChocoContrainte {
	implicit val format = Json.using[Json.WithDefaultValues].format[ChocoContrainte]
}

case class ChocoContrainte (
	                           idLieux: Seq[Int] = Seq.empty,
	                           nbHeureAnnuel: Int = 0,
	                           dureeMaxFormation: Int = 0,
	                           maxSemaineFormation: Int = 0,
	                           periodeFormationExclusion: Seq[ChocoPeriode] = Seq.empty,
	                           periodeFormationInclusion: Seq[ChocoPeriode] = Seq.empty,
	                           maxStagiaireEntrepriseEnFormation: Int = 0,
	                           stagiairesEntreprise: Seq[ChocoStagiaire] = Seq.empty,
	                           stagiairesRecquis: Seq[ChocoStagiaire] = Seq.empty
                           )