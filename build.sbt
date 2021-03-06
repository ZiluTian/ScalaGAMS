ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaGAMS",
    fork := true,
    javaOptions += "-Djava.library.path=" + file(".").getAbsolutePath + "/lib/GAMS",
    //javaOptions += "-Djava.library.path=" + file(".").getAbsolutePath + "/lib/GAMS_MAC",
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
