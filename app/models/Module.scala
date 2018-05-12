package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Module {
	implicit val format = Json.format[Module]
	implicit def fromResultSet(rs: ResultSet): Seq[Module] = {
		Utils.results(rs){
			case r => Module(
				r.getString("Libelle"),
				r.getInt("DureeEnHeures"),
				r.getInt("DureeEnSemaines"),
				r.getFloat("PrixPublicEnCours"),
				r.getString("LibelleCourt"),
				r.getInt("IdModule"),
				r.getBoolean("Archiver"),
				r.getInt("TypeModule")
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
