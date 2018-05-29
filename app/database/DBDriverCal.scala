package database

import models.Calendrier
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait CalendrierCollection {
	def save(calendrier: Calendrier): Future[WriteResult]
}

trait APICal {
	def close(): Future[Unit]
	
	val CalendrierCollection: CalendrierCollection
}

