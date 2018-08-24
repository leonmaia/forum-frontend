name := """forum-app"""
organization := "com.scale"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.webjars" % "bootstrap" % "4.1.3"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.3"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
