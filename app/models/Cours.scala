package models

import java.sql.ResultSet
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
														Debut: Instant,
														Fin: Instant,
														DureeReelleEnHeures: Int,
														CodePromotion: String,
														IdCours: String,
														PrixPublicAffecte: Float,
														IdModule: Int,
														LibelleCours: String,
														DureePrevueEnHeures: Int,
														DateAdefinir: Boolean,
														CodeSalle: String,
														CodeFormateur: Int,
														CodeLieu: Int,
                          )
