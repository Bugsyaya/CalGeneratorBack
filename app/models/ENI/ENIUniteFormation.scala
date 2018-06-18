package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENIUniteFormation {
	implicit val format = Json.format[ENIUniteFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[ENIUniteFormation] = {
		Utils.results(rs){
			case r => ENIUniteFormation(r.getString("libelle"),
				r.getInt("dureeEnHeures"),
				r.getInt("dureeEnSemaines"),
				r.getString("libelleCourt"),
				r.getInt("idUniteFormation")
			)}.toSeq
	}
}

case class ENIUniteFormation(
	                          libelle: String,
	                          dureeEnHeures: Int,
	                          dureeEnSemaines: Int,
	                          libelleCourt: String,
	                          idUniteFormation: Int
                          )
	
