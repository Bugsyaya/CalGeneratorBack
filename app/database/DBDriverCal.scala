package database

import models.Front.FrontProblem
import models.choco.{ChocoConstraint, ChocoModule}
import models.database.{Constraint, ConstraintModule, ForChocoModule}
import models.{Calendrier, ModuleFormation}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait CalendrierCollection {
	def save(calendrier: Calendrier): Future[WriteResult]
}

trait ModuleFormationCollection {
	def save(moduleFormation: ModuleFormation): Future[WriteResult]
	
	def byId(idModuleFormation: String): Future[Option[ModuleFormation]]
}

trait ConstraintModuleCollection {
	def save(chocoModules: Seq[ConstraintModule]): Future[Seq[WriteResult]]
	
//	def byId(idModule: String): Future[Option[ChocoModule]]
	
	def byId(chocoModuleId: Int): Future[Option[ConstraintModule]]
}

trait ProblemCollection {
	def create(problem: FrontProblem): Future[WriteResult]
	
	def update(problem: FrontProblem): Future[WriteResult]
	
	def byId(idProblem: String): Future[Option[FrontProblem]]
}

//trait ConstraintModuleCollection {
//	def create(chocoModule: ConstraintModule): Future[WriteResult]
//}

trait ChocoConstraintCollection {
	def create(chocoConstraint: ChocoConstraint): Future[WriteResult]
	
	def byId(chocoConstraintId: String): Future[Option[ChocoConstraint]]
}


trait ConstraintCollection {
	def create(constraint: Constraint): Future[WriteResult]
	
	def byId(constraintId: String): Future[Option[Constraint]]
	
	def all: Future[Seq[Constraint]]
}

trait ForChocModule {
	def save(forChocoModule: ForChocoModule): Future[WriteResult]
	
	def byIdModuleAndCodeFormation(idModule: Int, codeFormation: String): Future[Option[ChocoModule]]
	
	def byId(idForChocoModule: String): Future[Option[ForChocoModule]]
}

trait APICal {
	def close(): Future[Unit]
	
	val CalendrierCollection: CalendrierCollection
	
	val ModuleFormationCollection: ModuleFormationCollection
	
	val ProblemCollection: ProblemCollection
	
	val ConstraintModuleCollection: ConstraintModuleCollection
	
	val ChocoConstraintCollection: ChocoConstraintCollection
	
	val ConstraintCollection: ConstraintCollection
	
	val ForChocModule: ForChocModule
	
	//	val ChocoModuleCollection: ChocoModuleCollection
}
