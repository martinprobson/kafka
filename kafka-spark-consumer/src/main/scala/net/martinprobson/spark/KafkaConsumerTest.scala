package net.martinprobson.spark

import grizzled.slf4j.Logging
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.streaming.Trigger

object KafkaConsumerTest extends App with SparkEnv with Logging {

  info(s"Starting - $appName  ")

  import spark.implicits._
  // Create DataFrame representing the stream of input data from kafka topic.
  val lines = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", conf.getString("kafka.bootstrap_servers"))
    .option("subscribe",conf.getString("kafka.input_topic_name"))
    .option("startingOffsets",conf.getString("kafka.startingOffsets"))
    .option("failOnDataLoss",conf.getString("kafka.fail_on_data_loss"))
    .option("kafka.max.partition.fetch.bytes", "20000000")
    .load()
    .selectExpr("CAST(key AS STRING)","CAST(value AS STRING)")
    .as[(String,String)]


  // Start running the query that prints the running counts to the console
  val query = lines.writeStream
    .trigger(Trigger.ProcessingTime("10 minutes"))
    .foreachBatch{ (batchDf: Dataset[(String,String)], batchId: Long) => {
     val count = batchDf.count
     trace(s"batchDf = $batchDf, count = $count, batchId = $batchId")
    }}
    .start()

  query.awaitTermination()
}

