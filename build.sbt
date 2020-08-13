name := "Scala_Scrapbook"

version := "0.1"

scalaVersion := "2.12.10"

//libraryDependencies ++= Seq(
//  "org.scalanlp" %% "breeze" % "1.0", "org.scalanlp" %% "breeze-natives" % "1.0"
//)
//
//resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies ++= Seq(
  "org.apache.poi"               % "poi"                   % "4.1.0",
  "org.apache.poi"               % "poi-ooxml"             % "4.1.0",
  "org.scala-lang"               % "scala-reflect"         % scalaVersion.value
)

resolvers += Resolver.mavenCentral