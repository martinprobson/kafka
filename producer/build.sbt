name := "kafka_producer"

version := "0.1-SNAPSHOT"

scalaVersion := "3.3.6"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "4.0.0",
  "commons-logging" % "commons-logging" % "1.3.5",
  "org.slf4j" % "slf4j-api" % "2.0.17",
  "org.slf4j" % "slf4j-log4j12" % "2.0.17",
  "com.typesafe" % "config" % "1.4.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "org.apache.commons" % "commons-collections4" % "4.5.0"
)

scalacOptions ++= Seq(
//  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-explaintypes", // Explain type errors in more detail.
//  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Ywarn-dead-code" // Warn when dead code is identified.
)

