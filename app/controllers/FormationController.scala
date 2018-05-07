package controllers

import scala.concurrent.ExecutionContext

class FormationController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.FormationCollection.all.map(result => Ok(toJson[Seq[Formation]](result)))
	}
}
