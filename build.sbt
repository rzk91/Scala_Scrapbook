name := "Scala_Scrapbook"

version := "0.1"

ThisBuild / scalaVersion := "2.12.10"

resolvers += Resolver.mavenCentral

libraryDependencies ++= Seq(
  "org.apache.poi" % "poi"           % "4.1.0",
  "org.apache.poi" % "poi-ooxml"     % "4.1.0",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

// make run command include the provided dependencies
Compile / run := Defaults
  .runTask(fullClasspath in Compile, Compile / run / mainClass, Compile / run / runner)
  .evaluated
