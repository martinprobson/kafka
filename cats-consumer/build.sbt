val logging = Seq(
  "org.slf4j" % "slf4j-api" % "2.0.0-alpha4",
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha10",
  "ch.qos.logback" % "logback-core" % "1.3.0-alpha10",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
)

val kafka = Seq("org.apache.kafka" % "kafka-clients" % "2.1.0")

val cats_effect = Seq("org.typelevel" %% "cats-effect" % "3.3.6")

val config =
  Seq(
    "com.typesafe" % "config" % "1.4.1",
    "com.github.andr83" %% "scalaconfig" % "0.7",
    "com.github.pureconfig" %% "pureconfig" % "0.17.1",
    "com.github.pureconfig" %% "pureconfig-cats-effect" % "0.17.1"
  )

val test = Seq(
  "org.scalactic" %% "scalactic" % "3.2.10" % Test,
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

lazy val scala_sbt_template = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "cats-consumer",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= logging,
    libraryDependencies ++= config,
    libraryDependencies ++= kafka,
    libraryDependencies ++= cats_effect,
    libraryDependencies ++= test,
    scalaVersion := "2.13.8"
  )

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-explaintypes", // Explain type errors in more detail.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xsource:3", // Warn for Scala 3 features
  "-Ywarn-dead-code" // Warn when dead code is identified.
)

javacOptions ++= Seq("-source", "11", "-target", "11", "-Xlint")

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*)       => MergeStrategy.discard
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case _                                   => MergeStrategy.first
}
