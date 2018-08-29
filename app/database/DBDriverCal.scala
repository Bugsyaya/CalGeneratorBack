package database

import models.Front.{FrontModulePrerequis, FrontModulePrerequisPlanning, FrontProblem}
import models.choco.ChocoModule
import models.choco.Constraint.Entree.ChocoConstraint
import models.database.{Constraint, ConstraintModule}
import models.{Calendrier, ModuleFormation}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait CalendrierCollection {
	def save(calendrier: Calendrier): Future[WriteResult]
	
	def all: Future[Seq[Calendrier]]
	
	def update(calendrier: Calendrier): Future[WriteResult]
	
	def byId(idCalendar: String): Future[Option[Calendrier]]
	
	def byStatus(status: String): Future[Seq[Calendrier]]
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
	
	//	def update(problem: FrontProblem): Future[WriteResult]
	
	def byId(idProblem: String): Future[Option[FrontProblem]]
}

//trait ConstraintModuleCollection {
//	def create(chocoModule: ConstraintModule): Future[WriteResult]
//}

trait ChocoConstraintCollection {
	def create(chocoConstraint: ChocoConstraint): Future[WriteResult]
	
	def byId(chocoConstraintId: String): Future[Option[ChocoConstraint]]
}

trait ModulePrerequisCollection {
	def byId(id: String): Future[Option[FrontModulePrerequis]]
	
	def create(frontModulePrerequis: FrontModulePrerequis): Future[WriteResult]
	
	def all: Future[Seq[FrontModulePrerequis]]
}

trait ModulePrerequisPlanningCollection {
	def byId(id: String): Future[Option[FrontModulePrerequisPlanning]]
	
	def create(frontModulePrerequisPlanning: FrontModulePrerequisPlanning): Future[WriteResult]
	
	def all: Future[Seq[FrontModulePrerequisPlanning]]
}


trait ConstraintCollection {
	def create(constraint: Constraint): Future[WriteResult]
	
	def byId(constraintId: String): Future[Option[Constraint]]
	
	def all: Future[Seq[Constraint]]
}

trait ForChocoModule {
	def save(forChocoModule: ForChocoModule): Future[WriteResult]
	
	def byIdModuleAndCodeFormation(idModule: Int, codeFormation: String): Future[Option[ChocoModule]]
	
	//	def byId(idForChocoModule: String): Future[Option[ForChocoModule]]
}

trait APICal {
	def close(): Future[Unit]
	
	val CalendrierCollection: CalendrierCollection
	
	val ModulePrerequisCollection: ModulePrerequisCollection
	
	val ModulePrerequisPlanningCollection: ModulePrerequisPlanningCollection
	
	val ModuleFormationCollection: ModuleFormationCollection
	
	val ProblemCollection: ProblemCollection
	
	val ConstraintModuleCollection: ConstraintModuleCollection
	
	val ChocoConstraintCollection: ChocoConstraintCollection
	
	val ConstraintCollection: ConstraintCollection
	
	//	val ForChocModule: ForChocoModule
	
	//	val ChocoModuleCollection: ChocoModuleCollection
}
