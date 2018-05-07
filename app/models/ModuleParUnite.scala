package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object ModuleParUnite {
	implicit val format = Json.format[ModuleParUnite]
	implicit def fromResultSet(rs: ResultSet): Seq[ModuleParUnite] = {
		Utils.results(rs){
			case r => ModuleParUnite(r.getInt("position"),
				r.getInt("id"),
				r.getInt("idUnite"),
				r.getInt("idModule")
			)}.toSeq
	}
}

case class ModuleParUnite (
	                          position: Int,
	                          id: Int,
	                          idUnite: Int,
	                          idModule: Int
                          )