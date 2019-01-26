package net.martinprobson.spark

import grizzled.slf4j.Logging
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.Trigger

object KafkaConsumerTest extends App with SparkEnv with Logging {

  info(s"Starting - $appName  ")
  val servers = conf.getString("kafka.bootstrap_servers")
  info(s"kafka.bootstrap.servers = $servers")

  import spark.implicits._
  spark.sparkContext.setCheckpointDir("/tmp/checkpoint")
  // Create DataFrame representing the stream of input data from kafka topic.
  val lines = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", servers)
    .option("subscribe",conf.getString("kafka.input_topic_name"))
    .option("startingOffsets",conf.getString("kafka.startingOffsets"))
    .option("failOnDataLoss",conf.getString("kafka.fail_on_data_loss"))
    .option("kafka.max.partition.fetch.bytes", "20000000")
    .option("maxOffsetsPerTrigger",500)
    .load()
    .selectExpr("CAST(key AS STRING)","CAST(value AS STRING)")
    .as[(String,String)]


  // Start running the query that prints the running counts to the console
  val query = lines.writeStream
    .trigger(Trigger.ProcessingTime("10 seconds"))
    .option("checkpointLocation", "/tmp/checkpoint")
    .foreachBatch{ (batchDf: Dataset[(String,String)], batchId: Long) => {
      println(s"batchId = ${batchId} batchDF count = ${batchDf.repartition(100).count()}")
    }}
    .start()

  query.awaitTermination()
}

