package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIStagiaire {
	implicit val format: OFormat[ENIStagiaire] = Json.format[ENIStagiaire]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIStagiaire] = {
		Utils.results(rs) { r =>
			ENIStagiaire(
				r.getInt("codeStagiaire"),
				r.getString("civilite"),
				r.getString("nom"),
				r.getString("prenom"),
				r.getString("codePostal"),
				r.getString("ville"),
				r.getBoolean("permis")
			)
		}.toSeq
	}
}

case class ENIStagiaire(
	                       codeStagiaire: Int,
	                       civilite: String,
	                       nom: String,
	                       prenom: String,
	                       codePostal: String,
	                       ville: String,
	                       permis: Boolean
                       )