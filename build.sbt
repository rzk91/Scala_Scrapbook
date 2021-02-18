name := "Scala_Scrapbook"

version := "0.1"

ThisBuild / scalaVersion := "2.12.10"

val jacksonVersion = "2.10.2"

resolvers += Resolver.mavenCentral

libraryDependencies ++= Seq(
  "org.apache.poi"               % "poi"                   % "4.1.0",
  "org.apache.poi"               % "poi-ooxml"             % "4.1.0",
  "org.scala-lang"               % "scala-reflect"         % scalaVersion.value,
  "com.typesafe.scala-logging"   %% "scala-logging"        % "3.9.2",
  "org.slf4j"                    % "slf4j-log4j12"         % "1.7.25",
  "com.fasterxml.jackson.core"   % "jackson-databind"      % jacksonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
)

// make run command include the provided dependencies
Compile / run := Defaults
  .runTask(fullClasspath in Compile, Compile / run / mainClass, Compile / run / runner)
  .evaluated
