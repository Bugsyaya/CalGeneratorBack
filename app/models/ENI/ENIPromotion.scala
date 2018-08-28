package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIPromotion {
	implicit val format: OFormat[ENIPromotion] = Json.format[ENIPromotion]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIPromotion] = {
		Utils.results(rs) {
			r =>
				ENIPromotion(
					r.getString("codePromotion"),
					r.getString("libelle"),
					r.getString("debut"),
					r.getString("fin"),
					r.getString("codeFormation"),
					r.getInt("codeLieu")
				)
		}.toSeq
	}
}

case class ENIPromotion(
	                       codePromotion: String,
	                       libelle: String,
	                       debut: String,
	                       fin: String,
	                       codeFormation: String,
	                       codeLieu: Int
                       )
