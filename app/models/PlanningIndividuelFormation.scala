package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object PlanningIndividuelFormation {
	implicit val format = Json.format[PlanningIndividuelFormation]
	implicit def fromResultSet(rs: ResultSet): Seq[PlanningIndividuelFormation] = {
		Utils.results(rs){
			case r => PlanningIndividuelFormation(r.getInt("codePlanning"),
				r.getInt("codeStagiaire"),
				r.getString("dateCreation"),
				r.getString("codeFormation"),
				r.getString("CodePromotion")
			)}.toSeq
	}
}

case class PlanningIndividuelFormation (
	                                       codePlanning: Int,
	                                       codeStagiaire: Int,
	                                       dateCreation: String,
	                                       codeFormation: String,
	                                       CodePromotion: String
                                       )