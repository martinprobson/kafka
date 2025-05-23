package net.martinprobson.sparktest

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

trait SparkEnv {
  lazy private[sparktest] val conf = ConfigFactory.load
  lazy private[sparktest] val spark = getSession
  lazy private[sparktest] val appName = conf.getString("application.name")

  /**
    * Return some information on the environment we are running in.
    */
  private[sparktest] def versionInfo: Seq[String] = {
    val sc = getSession.sparkContext
    val scalaVersion = scala.util.Properties.scalaPropOrElse("version.number", "unknown")

    val versionInfo =
      s"""
         |---------------------------------------------------------------------------------
         | Scala version: $scalaVersion
         | Spark version: ${sc.version}
         | Spark master : ${sc.master}
         | Spark running locally? ${sc.isLocal}
         | Default parallelism: ${sc.defaultParallelism}
         |---------------------------------------------------------------------------------
         |""".stripMargin

    versionInfo.split("\n")
  }

  /**
    * Return spark session object
    *
    * NOTE Add .master("local") to enable debug via an IDE or add as a VM option at runtime
    * -Dspark.master="local[*]"
    */
  private def getSession: SparkSession =
    if (conf.getBoolean("application.hiveSupport"))
      SparkSession.builder
        .appName(appName)
        .master("local[5]")
        .enableHiveSupport()
        .getOrCreate()
    else
      SparkSession.builder
        .appName(appName)
        .master("local[5]")
        .getOrCreate()

  /*
	* Dump spark configuration for the current spark session.
	*/
  private[sparktest] def getAllConf: String = {
    getSession.conf.getAll.map { case (k, v) => "Key: [%s] Value: [%s]" format(k, v) } mkString("", "\n", "\n")
  }
}

