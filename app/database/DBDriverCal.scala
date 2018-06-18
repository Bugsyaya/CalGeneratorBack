package database

import models.choco.{ChocoConstraint, ChocoModule}
import models.{Calendrier, ModuleFormation, Problem}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait CalendrierCollection {
	def save(calendrier: Calendrier): Future[WriteResult]
}

trait ModuleFormationCollection {
	def save(moduleFormation: ModuleFormation): Future[WriteResult]
	
	def byId(idModuleFormation: String): Future[Option[ModuleFormation]]
}

trait ModuleContrainteCollection {
	def save(chocoModules: Seq[ChocoModule]): Future[Seq[WriteResult]]
	
//	def byId(idModule: String): Future[Option[ChocoModule]]
	
	def byId(chocoModuleId: Int): Future[Option[ChocoModule]]
}

trait ProblemCollection {
	
	def create(problem: Problem): Future[WriteResult]
	
	def update(problem: Problem): Future[WriteResult]
	def byId(idProblem: String): Future[Option[Problem]]
}

trait ChocoModuleCollection {
	
	def create(chocoModule: ChocoModule): Future[WriteResult]
}

trait ChocoConstraintCollection {
	
	def create(chocoConstraint: ChocoConstraint): Future[WriteResult]
}

trait APICal {
	def close(): Future[Unit]
	
	val CalendrierCollection: CalendrierCollection
	
	val ModuleFormationCollection: ModuleFormationCollection
	
	val ProblemCollection: ProblemCollection
	
	val ModuleContrainteCollection: ModuleContrainteCollection
	
	val ChocoConstraintCollection: ChocoConstraintCollection
	
	//	val ChocoModuleCollection: ChocoModuleCollection
}
