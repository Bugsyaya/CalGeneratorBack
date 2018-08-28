package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIEntreprise {
	implicit val format: OFormat[ENIEntreprise] = Json.format[ENIEntreprise]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIEntreprise] = {
		Utils.results(rs) {
			r =>
				ENIEntreprise(
					r.getInt("codeEntreprise"),
					r.getString("raisonSociale"),
					r.getString("codePostal"),
					r.getString("ville"),
					r.getString("email"),
					r.getString("nomCommercial"),
					r.getInt("siret"),
					r.getInt("codeContactEni"),
					r.getInt("codeOrganismeFavoris")
				)
		}.toSeq
	}
}

case class ENIEntreprise(
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
