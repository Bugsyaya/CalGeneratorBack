package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENICours {
	implicit val format = Json.format[ENICours]
	implicit def fromResultSet(rs: ResultSet): Seq[ENICours] = {
		Utils.results(rs){
			case r => ENICours(
				r.getString("debut"),
				r.getString("fin"),
				r.getInt("dureeReelleEnHeures"),
				r.getString("codePromotion"),
				r.getString("idCours"),
				r.getInt("idModule"),
				r.getString("libelleCours"),
				r.getInt("dureePrevueEnHeures"),
				r.getBoolean("dateAdefinir"),
//				r.getString("codeSalle"),
				r.getInt("codeLieu")
			)}.toSeq
	}
}

case class ENICours(
	                 debut: String = "",
	                 fin: String = "",
	                 dureeReelleEnHeures: Int = 0,
	                 codePromotion: String = "",
	                 idCours: String = "",
	                 idModule: Int = 0,
	                 libelleCours: String = "",
	                 dureePrevueEnHeures: Int = 0,
	                 dateAdefinir: Boolean = false,
//	                 codeSalle: String = "",
	                 codeLieu: Int = 0
                 )