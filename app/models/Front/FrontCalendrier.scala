package models.Front

import models.choco.{ChocoContrainte, ChocoPeriode}
import play.api.libs.json.Json

object FrontCalendrier {
	implicit val format = Json.format[FrontCalendrier]
}

case class FrontCalendrier (
	                           codeFormation: String,
	                           periodeFormation: ChocoPeriode,
	                           contraintes: Seq[ChocoContrainte] = Nil
                           )