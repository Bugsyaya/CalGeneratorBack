package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Promotion {
	implicit val format = Json.format[Promotion]
	implicit def fromResultSet(rs: ResultSet): Seq[Promotion] = {
		Utils.results(rs){
			case r => Promotion(
				r.getString("codePromotion"),
				r.getString("libelle"),
				r.getString("debut"),
				r.getString("fin"),
				r.getString("codeFormation"),
				r.getInt("codeLieu")
			)}.toSeq
	}
}

case class Promotion (
	                 codePromotion: String,
	                 libelle: String,
	                 debut: String,
	                 fin: String,
	                 codeFormation: String,
	                 codeLieu: Int
                 )
