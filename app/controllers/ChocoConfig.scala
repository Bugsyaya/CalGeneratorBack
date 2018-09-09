package controllers

import scala.sys.env

object ChocoConfig {
	def baseUrl: String = env.getOrElse("CHOCO_URL", "http://localhost:8000")
}
