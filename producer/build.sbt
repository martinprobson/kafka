name := "kafka_producer"

version := "0.1-SNAPSHOT"

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.1.0",
  "commons-logging" % "commons-logging" % "1.1.3",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-log4j12" % "1.7.5",
  "org.clapper" %% "grizzled-slf4j" % "1.3.4",
  "com.typesafe" % "config" % "1.3.2",
  "org.apache.commons" % "commons-collections4" % "4.4"
)

scalacOptions ++= Seq(
//  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-explaintypes", // Explain type errors in more detail.
//  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xsource:3", // Warn for Scala 3 features
  "-Ywarn-dead-code" // Warn when dead code is identified.
)

