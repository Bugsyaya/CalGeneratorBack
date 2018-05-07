package controllers

import scala.concurrent.ExecutionContext

class ModulesParUnitesController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.ModuleParUniteCollection.all.map(result => Ok(toJson[Seq[ModuleParUnite]](result)))
	}
}
