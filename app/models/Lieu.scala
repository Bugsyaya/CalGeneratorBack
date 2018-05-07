package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Lieu {
	implicit val format = Json.format[Lieu]
	implicit def fromResultSet(rs: ResultSet): Seq[Lieu] = {
		Utils.results(rs){
			case r => Lieu(
				r.getInt("codeLieu"),
				r.getString("libelle"),
				r.getBoolean("archive"),
				r.getBoolean("gestionEmargement"),
				r.getString("debutAM"),
				r.getString("finAM"),
				r.getString("debutPM"),
				r.getString("finPM"),
				r.getString("adresse"),
				r.getInt("cP"),
				r.getString("ville")
			)}.toSeq
	}
}

case class Lieu (
	                codeLieu: Int,
	                libelle: String,
	                archive: Boolean,
	                gestionEmargement: Boolean,
	                debutAM: String,
	                finAM: String,
	                debutPM: String,
	                finPM: String,
	                adresse: String,
	                cP: Int,
	                ville: String
                     )