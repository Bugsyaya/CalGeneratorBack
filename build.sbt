name := "CalGeneratorBack"
 
version := "1.0" 
      
lazy val `calgeneratorback` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.6.3"
//libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "6.1.0.jre8" % Test

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      