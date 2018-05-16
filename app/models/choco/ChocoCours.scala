package models.choco

import play.api.libs.json.Json

object ChocoCours {
	implicit val format = Json.format[ChocoCours]
}

case class ChocoCours (
	                      periode: ChocoPeriode,
	                      idCours: String,
	                      idModule: Int,
	                      lieu: Int,
	                      nbHeureReel: Int
                        )