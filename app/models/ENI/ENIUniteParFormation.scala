package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENIUniteParFormation {
	implicit val format = Json.format[ENIUniteParFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[ENIUniteParFormation] = {
		Utils.results(rs){
			case r => ENIUniteParFormation(r.getString("codeFormation"),
				r.getInt("position"),
				r.getInt("id"),
				r.getInt("idUniteFormation")
			)}.toSeq
	}
}

case class ENIUniteParFormation(
	                             codeFormation: String,
	                             position: Int,
	                             id: Int,
	                             idUniteFormation: Int
                          )
