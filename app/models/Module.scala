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
				r.getFloat("prixPublicEnCours"),
				r.getString("libelleCourt"),
				r.getInt("idModule"),
				r.getBoolean("archiver"),
				r.getInt("typeModule")
			)}.toSeq
	}
}

case class Module (
	                          libelle: String,
	                          dureeEnHeures: Int,
	                          dureeEnSemaines: Int,
	                          prixPublicEnCours: Float,
	                          libelleCourt: String,
	                          idModule: Int,
	                          archiver: Boolean,
	                          typeModule: Int
                          )