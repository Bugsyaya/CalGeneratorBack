package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Formation {
	implicit val format = Json.format[Formation]
	implicit def fromResultSet(rs: ResultSet): Seq[Formation] = {
		Utils.results(rs){
			case r => Formation(
				r.getString("CodeFormation"),
				r.getString("LibelleLong"),
				r.getString("LibelleCourt"),
				r.getInt("DureeEnHeures"),
				r.getFloat("TauxHoraire"),
				r.getString("CodeTitre"),
				r.getFloat("PrixPublicEnCours"),
				r.getInt("HeuresCentre"),
				r.getInt("HeuresStage"),
				r.getInt("SemainesCentre"),
				r.getInt("SemainesStage"),
				r.getInt("DureeEnSemaines"),
				r.getBoolean("Archiver"),
				r.getInt("ECFaPasser"),
				r.getInt("TypeFormation"),
				r.getInt("CodeLieu")
			)}.toSeq
	}
}

case class Formation (
	                     CodeFormation: String,
	                     LibelleLong: String,
	                     LibelleCourt: String,
	                     DureeEnHeures: Int,
	                     TauxHoraire: Float,
	                     CodeTitre: String,
	                     PrixPublicEnCours: Float,
	                     HeuresCentre: Int,
	                     HeuresStage: Int,
	                     SemainesCentre: Int,
	                     SemainesStage: Int,
	                     DureeEnSemaines: Int,
	                     Archiver: Boolean,
	                     ECFaPasser: Int,
	                     TypeFormation: Int,
	                     CodeLieu: Int
                      )