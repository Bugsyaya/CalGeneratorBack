package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIModuleParUnite {
	implicit val format: OFormat[ENIModuleParUnite] = Json.format[ENIModuleParUnite]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIModuleParUnite] = {
		Utils.results(rs) {
			r =>
				ENIModuleParUnite(r.getInt("position"),
					r.getInt("id"),
					r.getInt("idUnite"),
					r.getInt("idModule")
				)
		}.toSeq
	}
}

case class ENIModuleParUnite(
	                            position: Int,
	                            id: Int,
	                            idUnite: Int,
	                            idModule: Int
                            )