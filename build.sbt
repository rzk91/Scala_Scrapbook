name := "Scrapbook_Scala"

version := "0.1"

val _scalaVersion = "2.12.10"

ThisBuild / scalaVersion        := _scalaVersion
Global / concurrentRestrictions += Tags.limit(Tags.Test, 1)

resolvers += Resolver.mavenCentral

lazy val root = (project in file(".")).settings(libraryDependencies ++= normalDependencies ++ testDependencies)

val jacksonVersion = "2.10.2"

val normalDependencies = Seq(
  "org.apache.poi"               % "poi"                   % "4.1.0",
  "org.apache.poi"               % "poi-ooxml"             % "4.1.0",
  "org.scala-lang"               % "scala-reflect"         % _scalaVersion,
  "com.typesafe.scala-logging"   %% "scala-logging"        % "3.9.2",
  "org.slf4j"                    % "slf4j-log4j12"         % "1.7.25",
  "com.fasterxml.jackson.core"   % "jackson-databind"      % jacksonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
)

val testDependencies = Seq("org.scalatest" %% "scalatest" % "3.2.9" % "test")

// make run command include the provided dependencies
Compile / run := Defaults
  .runTask(fullClasspath in Compile, Compile / run / mainClass, Compile / run / runner)
  .evaluated
