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
				r.getInt("dureeEnHeures"),
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
														codeFormation: String,
														libelleLong: String,
														libelleCourt: String,
														dureeEnHeures: Int,
														tauxHoraire: Float,
														codeTitre: String,
														prixPublicEnCours: Float,
														heuresCentre: Int,
														heuresStage: Int,
														semainesCentre: Int,
														semainesStage: Int,
														dureeEnSemaines: Int,
														archiver: Boolean,
														eCFaPasser: Int,
														typeFormation: Int,
														codeLieu: Int
                          )
