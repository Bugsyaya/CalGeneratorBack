package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Cours {
	implicit val format = Json.format[Cours]
	implicit def fromResultSet(rs: ResultSet): Seq[Cours] = {
		Utils.results(rs){
			case r => Cours(
				r.getString("debut"),
				r.getString("fin"),
				r.getInt("dureeReelleEnHeures"),
				r.getString("codePromotion"),
				r.getString("idCours"),
				r.getFloat("prixPublicAffecte"),
				r.getInt("idModule"),
				r.getString("libelleCours"),
				r.getInt("dureePrevueEnHeures"),
				r.getBoolean("dateAdefinir"),
				r.getString("codeSalle"),
				r.getInt("codeFormateur"),
				r.getInt("codeLieu")
			)}.toSeq
	}
}

case class Cours (
	                 debut: String,
	                 fin: String,
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
	                 codeLieu: Int
                 )