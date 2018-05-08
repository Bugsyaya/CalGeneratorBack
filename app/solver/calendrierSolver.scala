package models

import java.time.Instant

import database.ENIDB

import scala.concurrent.Future

trait CalendrierCollection {
	def search(idFormation: Int, debut: Instant, fin: Instant): Future[Seq[Calendrier]]
	def byFormation(idFormation: Int): Future[Option[Module]]
	def byIdCalendrier(idModule: Int): Future[Option[Module]]
}

trait API {
	val CalendrierCollection: CalendrierCollection
}


case class calendrierSolver(db : ENIDB) {



	def search(idFormation: Int, debut: Instant, fin: Instant): Future[CalendrierCollection] = {

	}

}



