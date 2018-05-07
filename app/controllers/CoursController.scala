package controllers

import scala.concurrent.ExecutionContext

class CoursController @Inject()(cc : ControllerComponents) extends AbstractController(cc){
	val db = ENIDB(DBDriverENI())
	
	implicit val system: ActorSystem = ActorSystem()
	implicit val ec: ExecutionContext = system.dispatcher
	implicit val mat: ActorMaterializer = ActorMaterializer()
	
	def show = Action.async {
		db.CoursCollection.all.map(result => Ok(toJson[Seq[Cours]](result)))
	}
}
