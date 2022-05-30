package net.martinprobson.spark

import java.util.Properties
import com.typesafe.config.Config
import grizzled.slf4j.Logging
import org.apache.commons.collections4.MapUtils
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Main extends App with Configured with Logging {
  info("Main")
  trace("LOG TEST")

  //TODO Do this in spark in parallel.
  val producer = new KafkaProducer[String,String](Util.propsFromConfig(conf.getConfig("kafka_producer")))
  val topic = conf.getString("topic.name")
  val msgCount = conf.getInt("topic.message_count")
  (1 to msgCount).foreach(i => {
    producer.send(new ProducerRecord[String,String](topic,i.toString,i.toString))
  })
  producer.close()
  info("Done")

}
