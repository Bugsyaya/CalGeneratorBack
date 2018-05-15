package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object UniteFormation {
	implicit val format = Json.format[UniteFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[UniteFormation] = {
		Utils.results(rs){
			case r => UniteFormation(r.getString("libelle"),
				r.getInt("dureeEnHeures"),
				r.getInt("dureeEnSemaines"),
				r.getString("libelleCourt"),
				r.getInt("idUniteFormation")
			)}.toSeq
	}
}

case class UniteFormation (
	                          libelle: String,
	                          dureeEnHeures: Int,
	                          dureeEnSemaines: Int,
	                          libelleCourt: String,
	                          idUniteFormation: Int
                          )
	
