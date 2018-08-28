package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIModule {
	implicit val format: OFormat[ENIModule] = Json.format[ENIModule]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIModule] = {
		Utils.results(rs) {
			r =>
				ENIModule(r.getString("libelle"),
					r.getInt("dureeEnHeures"),
					r.getInt("dureeEnSemaines"),
					r.getString("libelleCourt"),
					r.getInt("idModule"),
					r.getInt("typeModule")
				)
		}.toSeq
	}
}

case class ENIModule(
	                    libelle: String,
	                    dureeEnHeures: Int,
	                    dureeEnSemaines: Int,
	                    libelleCourt: String,
	                    idModule: Int,
	                    typeModule: Int
                    )