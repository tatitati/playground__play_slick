name := """shop"""
organization := "com.example"
version := "1.0-SNAPSHOT"
lazy val root = (project in file(".")).enablePlugins(PlayScala)
scalaVersion := "2.12.6"

// DI
routesGenerator := InjectedRoutesGenerator

// Production
libraryDependencies += guice
//  libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.21"
libraryDependencies += "mysql" % "mysql-connector-java" % "latest.release"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "latest.release"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "latest.release"
// Dev
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-all" % "1.8.4" % Test


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"