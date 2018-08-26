package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENIModuleParUnite {
	implicit val format = Json.format[ENIModuleParUnite]
	implicit def fromResultSet(rs: ResultSet): Seq[ENIModuleParUnite] = {
		Utils.results(rs){
			case r => ENIModuleParUnite(r.getInt("position"),
				r.getInt("id"),
				r.getInt("idUnite"),
				r.getInt("idModule")
			)}.toSeq
	}
}

case class ENIModuleParUnite(
	                          position: Int,
	                          id: Int,
	                          idUnite: Int,
	                          idModule: Int
                          )