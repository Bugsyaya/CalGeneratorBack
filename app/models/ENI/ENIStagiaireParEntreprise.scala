package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENIStagiaireParEntreprise {
	implicit val format = Json.format[ENIStagiaireParEntreprise]
	implicit def fromResultSet(rs: ResultSet): Seq[ENIStagiaireParEntreprise] = {
		Utils.results(rs){
			case r => ENIStagiaireParEntreprise(r.getString("CodeStagiaire"),
				r.getInt("CodeEntreprise"),
				r.getString("titreVise")
			)}.toSeq
	}
}

case class ENIStagiaireParEntreprise(
	                                  codeStagiaire: String,
	                                  codeEntreprise: Int,
	                                  titreVise: String
                          )