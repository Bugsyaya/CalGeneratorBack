package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIUniteParFormation {
	implicit val format: OFormat[ENIUniteParFormation] = Json.format[ENIUniteParFormation]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIUniteParFormation] = {
		Utils.results(rs) {
			r =>
				ENIUniteParFormation(r.getString("codeFormation"),
					r.getInt("position"),
					r.getInt("id"),
					r.getInt("idUniteFormation")
				)
		}.toSeq
	}
}

case class ENIUniteParFormation(
	                               codeFormation: String,
	                               position: Int,
	                               id: Int,
	                               idUniteFormation: Int
                               )
