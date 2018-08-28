package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENIFormation {
	implicit val format: OFormat[ENIFormation] = Json.format[ENIFormation]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENIFormation] = {
		Utils.results(rs) {
			r =>
				ENIFormation(
					r.getString("codeFormation"),
					r.getString("libelleLong"),
					r.getString("libelleCourt"),
					r.getInt("dureeEnHeures"),
					r.getFloat("tauxHoraire"),
					r.getString("codeTitre"),
					r.getInt("heuresCentre"),
					r.getInt("heuresStage"),
					r.getInt("semainesCentre"),
					r.getInt("semainesStage"),
					r.getInt("dureeEnSemaines"),
					r.getInt("typeFormation"),
					r.getInt("codeLieu")
				)
		}.toSeq
	}
}

case class ENIFormation(
	                       codeFormation: String,
	                       libelleLong: String,
	                       libelleCourt: String,
	                       dureeEnHeures: Int,
	                       tauxHoraire: Float,
	                       codeTitre: String,
	                       heuresCentre: Int,
	                       heuresStage: Int,
	                       semainesCentre: Int,
	                       semainesStage: Int,
	                       dureeEnSemaines: Int,
	                       typeFormation: Int,
	                       codeLieu: Int
                       )