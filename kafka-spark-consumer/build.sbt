lazy val root = (project in file("."))
    .settings(
      name := "KafkaConsumerTest",
      organization := "net.martinprobson",
      scalaVersion := "2.13.16",
      version := "0.1"
)

val sparkVersion = "3.5.5"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.kafka" % "kafka-clients" % "0.10.0.0",
//  "org.apache.hadoop" % "hadoop-common" % "2.9.0",
  "org.slf4j" % "slf4j-log4j12" % "1.7.25",
  "org.clapper" %% "grizzled-slf4j" % "1.3.4",
  "com.typesafe" % "config" % "1.3.2"
)


assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("org.apache.kafka.**" -> "kk.@1").inAll,
  ShadeRule.rename("org.apache.spark.streaming.kafka010.**" -> "skk.@1").inAll
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case _ => MergeStrategy.first
}

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "_" + sv.binary + "-" + sparkVersion + "_" + module.revision + "." + artifact.extension
}

