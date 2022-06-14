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

scalacOptions ++= Seq(
  "-deprecation",                     // Emit warning and location for usages of deprecated APIs.
  "-encoding",                        // Specify character encoding used by source files
  "utf-8",                            // to be utf-8.
  "-explaintypes",                    // Explain type errors in more detail.
  "-feature",                         // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials",           // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros",    // Allow macro definition (besides implementation and application)
  "-language:higherKinds",            // Allow higher-kinded types
  "-language:implicitConversions",    // Allow definition of implicit functions called views
  "-unchecked",                       // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit",                      // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfuture",                         // Turn on future language features.
  "-Xlint:adapted-args",              // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:constant",                  // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select",        // Selecting member of DelayedInit.
  "-Xlint:doc-detached",              // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible",              // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any",                 // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator",      // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override",          // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit",              // Warn when nullary methods return Unit.
  "-Xlint:option-implicit",           // Option.apply used implicit view.
  "-Xlint:package-object-classes",    // Class or object defined in package object.
  "-Xlint:poly-implicit-overload",    // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow",            // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align",               // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow",     // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match",             // Pattern match may not be typesafe.
  "-Yno-adapted-args",                // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ypartial-unification",            // Enable partial unification in type constructor inference
  "-Ywarn-dead-code",                 // Warn when dead code is identified.
  "-Ywarn-extra-implicit",            // Warn when more than one implicit parameter section is defined.
  "-Ywarn-inaccessible",              // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any",                 // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override",          // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit",              // Warn when nullary methods return Unit.
  "-Ywarn-unused:patvars"             // Warn if a variable bound in a pattern is unused.
)
