package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object StagiaireParEntreprise {
	implicit val format = Json.format[StagiaireParEntreprise]
	implicit def fromResultSet(rs: ResultSet): Seq[StagiaireParEntreprise] = {
		Utils.results(rs){
			case r => StagiaireParEntreprise(r.getString("CodeStagiaire"),
				r.getInt("CodeEntreprise"),
				r.getString("titreVise")
			)}.toSeq
	}
}

case class StagiaireParEntreprise (
	                                  codeStagiaire: String,
	                                  codeEntreprise: Int,
	                                  titreVise: String
                          )