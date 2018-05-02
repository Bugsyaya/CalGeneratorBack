package database

import java.sql.{Connection, DriverManager, ResultSet}
import scala.concurrent.ExecutionContext.Implicits.global

import models.UniteFormation

import scala.concurrent.Future
import scala.util.control.NonFatal


trait UniteFormationCollection {
	def all: Future[Seq[UniteFormation]]
}

trait API {
	val UniteFormationCollection: UniteFormationCollection
}

case class ENIConf(
	                  host: String = "localhost",
	                  port: Int = 1433,
	                  dbName: String = "master",
	                  login: String = "sa",
	                  password: String = "yourStrong(!)Password"
                  )

case class DBDriverENI(conf: ENIConf) {
	val uri = s"""jdbc:sqlserver://${conf.host}:${conf.port};databaseName=${conf.dbName};user=${conf.login};password=${conf.password};"""
	
	lazy val sqlConn = Future.successful(DriverManager.getConnection(uri))
	
	lazy val defaultDB: Future[Connection] =
		(for {
			db <- sqlConn
		} yield db)
			.recoverWith { case NonFatal(ex) => Future.failed(new RuntimeException(s"MongoDb not reachable $uri", ex)) }
	
	def close(): Future[Unit] = {
		println(s"############ Closing DB... ${conf.port} #####################")
		sqlConn.map(_.close())
	}
	
	def query[T](query: String)(implicit imp: ResultSet => T): Future[T] = sqlConn.map(_.prepareStatement(query).executeQuery())
}


