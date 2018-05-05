package models

import java.sql.ResultSet
import helper._
import play.api.libs.json.Json

object Salle {
	implicit val format = Json.format[Salle]
	implicit def fromResultSet(rs: ResultSet): Seq[Salle] = {
		Utils.results(rs){
			case r => Salle(r.getString("codeSalle"),
				r.getString("libelle"),
				r.getInt("capacite"),
				r.getInt("lieu")
			)}.toSeq
	}
}

case class Salle (
	                 codeSalle: String,
	                 libelle: String,
	                 capacite: Int,
	                 lieu: Int
                  )