package models.ENI

import java.sql.ResultSet

import helper._
import play.api.libs.json.{Json, OFormat}

object ENILieu {
	implicit val format: OFormat[ENILieu] = Json.format[ENILieu]
	
	implicit def fromResultSet(rs: ResultSet): Seq[ENILieu] = {
		Utils.results(rs) {
			r =>
				ENILieu(
					r.getInt("codeLieu"),
					r.getString("libelle"),
					r.getString("debutAM"),
					r.getString("finAM"),
					r.getString("debutPM"),
					r.getString("finPM"),
					r.getString("adresse"),
					r.getInt("cP"),
					r.getString("ville")
				)
		}.toSeq
	}
}

case class ENILieu(
	                  codeLieu: Int,
	                  libelle: String,
	                  debutAM: String,
	                  finAM: String,
	                  debutPM: String,
	                  finPM: String,
	                  adresse: String,
	                  cP: Int,
	                  ville: String
                  )