package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Entreprise {
	implicit val format = Json.format[Entreprise]
	implicit def fromResultSet(rs: ResultSet): Seq[Entreprise] = {
		Utils.results(rs){
			case r => Entreprise(
				r.getInt("codeEntreprise"),
				r.getString("codeTypeEntreprise"),
				r.getString("codeRegion"),
				r.getInt("codeSecteur"),
				r.getInt("codeOrganisme"),
				r.getString("nomCommercial"),
				r.getInt("siret"),
				r.getInt("codeContactEni"),
				r.getInt("codeOrganismeFavoris")
			)}.toSeq
	}
}

case class Entreprise (
	                      codeEntreprise: Int,
	                      codeTypeEntreprise: String,
	                      codeRegion: String,
	                      codeSecteur: Int,
	                      codeOrganisme: Int,
	                      nomCommercial: String,
	                      siret: Int,
	                      codeContactEni: Int,
	                      codeOrganismeFavoris: Int
                 )
