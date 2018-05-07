package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json
import java.util.Date
import java.text.SimpleDateFormat
import java.time.{Instant, ZoneId}

object Cours {
	implicit val format = Json.format[Cours]
	implicit def fromResultSet(rs: ResultSet): Seq[Cours] = {
		Utils.results(rs){
			case r => Cours(
				r.getDate("Debut").toLocalDate().atStartOfDay(ZoneId.of("UTC")).toInstant(),
				r.getDate("Fin").toLocalDate().atStartOfDay(ZoneId.of("UTC")).toInstant(),
				r.getInt("DureeReelleEnHeures"),
				r.getString("CodePromotion"),
				r.getString("IdCours"),
				r.getFloat("PrixPublicAffecte"),
				r.getInt("IdModule"),
				r.getString("LibelleCours"),
				r.getInt("DureePrevueEnHeures"),
				r.getBoolean("DateAdefinir"),
				r.getString("CodeSalle"),
				r.getInt("CodeFormateur"),
				r.getInt("CodeLieu"),
			)}.toSeq

	}
}

case class Cours (
														debut: Instant,
														fin: Instant,
														dureeReelleEnHeures: Int,
														codePromotion: String,
														idCours: String,
														prixPublicAffecte: Float,
														idModule: Int,
														libelleCours: String,
														dureePrevueEnHeures: Int,
														dateAdefinir: Boolean,
														codeSalle: String,
														codeFormateur: Int,
														codeLieu: Int,
                          )
