package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENIPlanningIndividuelFormation {
	implicit val format = Json.format[ENIPlanningIndividuelFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[ENIPlanningIndividuelFormation] = {
		Utils.results(rs){
			case r => ENIPlanningIndividuelFormation(r.getInt("codePlanning"),
				r.getInt("codeStagiaire"),
				r.getString("dateCreation"),
				r.getString("codeFormation"),
				r.getString("CodePromotion")
			)}.toSeq
	}
}

case class ENIPlanningIndividuelFormation(
	                                       codePlanning: Int,
	                                       codeStagiaire: Int,
	                                       dateCreation: String,
	                                       codeFormation: String,
	                                       CodePromotion: String
                                       )