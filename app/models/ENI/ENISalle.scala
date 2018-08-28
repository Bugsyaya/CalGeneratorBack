package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENISalle {
	implicit val format: OFormat[ENISalle] = Json.format[ENISalle]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENISalle] = {
		Utils.results(rs) {
			r =>
				ENISalle(r.getString("codeSalle"),
					r.getString("libelle"),
					r.getInt("capacite"),
					r.getInt("lieu")
				)
		}.toSeq
	}
}

case class ENISalle(
	                   codeSalle: String,
	                   libelle: String,
	                   capacite: Int,
	                   lieu: Int
                   )