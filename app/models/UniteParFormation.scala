package models

import java.sql.ResultSet

object UniteParFormation {
	implicit val format = Json.format[UniteParFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[UniteParFormation] = {
		Utils.results(rs){
			case r => UniteParFormation(r.getString("CodeFormation"),
				r.getInt("Position"),
				r.getInt("Id"),
				r.getInt("IdUniteFormation")
			)}.toSeq
	}
}

case class UniteParFormation (
														CodeFormation: String,
														Position: Int,
														Id: Int,
														IdUniteFormation: Int
                          )
