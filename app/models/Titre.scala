package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Titre {
	implicit val format = Json.format[Titre]
	implicit def fromResultSet(rs: ResultSet): Seq[Titre] = {
		Utils.results(rs){
			case r => Titre(r.getString("codeTitre"),
				r.getString("libelleCourt"),
				r.getString("libelleLong"),
				r.getString("niveau"),
				r.getString("codeAFPA"),
				r.getString("millesime")
			)}.toSeq
	}
}

case class Titre (
	                 codeTitre: String,
	                 libelleCourt: String,
	                 libelleLong: String,
	                 niveau: String,
	                 codeAFPA: String,
	                 millesime: String
                 )