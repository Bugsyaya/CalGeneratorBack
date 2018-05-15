package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Module {
	implicit val format = Json.format[Module]
	implicit def fromResultSet(rs: ResultSet): Seq[Module] = {
		Utils.results(rs){
			case r => Module(r.getString("libelle"),
				r.getInt("dureeEnHeures"),
				r.getInt("dureeEnSemaines"),
				r.getString("libelleCourt"),
				r.getInt("idModule"),
				r.getInt("typeModule")
			)}.toSeq
	}
}

case class Module (
	                          libelle: String,
	                          dureeEnHeures: Int,
	                          dureeEnSemaines: Int,
	                          libelleCourt: String,
	                          idModule: Int,
	                          typeModule: Int
                          )