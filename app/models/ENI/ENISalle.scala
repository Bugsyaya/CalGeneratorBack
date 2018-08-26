package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.Json

object ENISalle {
	implicit val format = Json.format[ENISalle]
	implicit def fromResultSet(rs: ResultSet): Seq[ENISalle] = {
		Utils.results(rs){
			case r => ENISalle(r.getString("codeSalle"),
				r.getString("libelle"),
				r.getInt("capacite"),
				r.getInt("lieu")
			)}.toSeq
	}
}

case class ENISalle(
	                 codeSalle: String,
	                 libelle: String,
	                 capacite: Int,
	                 lieu: Int
                  )