package models

import java.sql.ResultSet

object ModuleParUnite {
	implicit val format = Json.format[ModuleParUnite]
	implicit def fromResultSet(rs: ResultSet): Seq[ModuleParUnite] = {
		Utils.results(rs){
			case r => ModuleParUnite(
				r.getInt("Position"),
				r.getInt("IdUnite"),
				r.getInt("IdModule")
			)}.toSeq
	}
}

case class ModuleParUnite (
														Position: Int,
														IdUnite: Int,
														IdModule : Int
                          )
