package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENITitre {
	implicit val format: OFormat[ENITitre] = Json.format[ENITitre]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENITitre] = {
		Utils.results(rs) {
			r =>
				ENITitre(r.getString("codeTitre"),
					r.getString("libelleCourt"),
					r.getString("libelleLong"),
					r.getString("niveau"),
					r.getString("millesime")
				)
		}.toSeq
	}
}

case class ENITitre(
	                   codeTitre: String,
	                   libelleCourt: String,
	                   libelleLong: String,
	                   niveau: String,
	                   millesime: String
                   )