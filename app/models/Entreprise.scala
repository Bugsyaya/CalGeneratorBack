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
				r.getString("codePostal"),
				r.getString("ville"),
				r.getString("email"),
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
	                      codePostal: String,
	                      ville: String,
	                      email: String,
	                      nomCommercial: String,
	                      siret: Int,
	                      codeContactEni: Int,
	                      codeOrganismeFavoris: Int
                 )
