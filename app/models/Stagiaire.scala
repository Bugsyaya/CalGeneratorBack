package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Stagiaire {
	implicit val format = Json.format[Stagiaire]
	implicit def fromResultSet(rs: ResultSet): Seq[Stagiaire] = {
		Utils.results(rs){r =>
			Stagiaire(
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

case class Stagiaire (
	                     codeStagiaire: Int,
	                     civilite: String,
	                     nom: String,
	                     prenom: String,
	                     codePostal: String,
	                     ville: String,
	                     permis: Boolean
                     )