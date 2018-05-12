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
				r.getInt("idModule"),
				r.getString("libelleCours"),
				r.getInt("dureePrevueEnHeures"),
				r.getBoolean("dateAdefinir"),
				r.getString("codeSalle"),
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
	                 idModule: Int,
	                 libelleCours: String,
	                 dureePrevueEnHeures: Int,
	                 dateAdefinir: Boolean,
	                 codeSalle: String,
	                 codeLieu: Int
                 )