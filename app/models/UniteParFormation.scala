package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object UniteParFormation {
	implicit val format = Json.format[UniteParFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[UniteParFormation] = {
		Utils.results(rs){
			case r => UniteParFormation(r.getString("codeFormation"),
				r.getInt("position"),
				r.getInt("id"),
				r.getInt("idUniteFormation")
			)}.toSeq
	}
}

case class UniteParFormation (
	                             codeFormation: String,
	                             position: Int,
	                             id: Int,
	                             idUniteFormation: Int
                          )