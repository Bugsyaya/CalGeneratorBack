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
				r.getString("raisonSociale"),
				r.getString("adresse1"),
				r.getString("adresse2"),
				r.getString("adresse3"),
				r.getString("codePostal"),
				r.getString("ville"),
				r.getString("telephone"),
				r.getString("fax"),
				r.getString("siteWeb"),
				r.getString("email"),
				r.getString("observation"),
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
	                      raisonSociale: String,
	                      adresse1: String,
	                      adresse2: String,
	                      adresse3: String,
	                      codePostal: String,
	                      ville: String,
	                      telephone: String,
	                      fax: String,
	                      siteWeb: String,
	                      email: String,
	                      observation: String,
	                      codeTypeEntreprise: String,
	                      codeRegion: String,
	                      codeSecteur: Int,
	                      codeOrganisme: Int,
	                      nomCommercial: String,
	                      siret: Int,
	                      codeContactEni: Int,
	                      codeOrganismeFavoris: Int
                 )
