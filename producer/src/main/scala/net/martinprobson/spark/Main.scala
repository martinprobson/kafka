package net.martinprobson.spark

import java.util.Properties

import com.typesafe.config.Config
import grizzled.slf4j.Logging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Main extends App with Configured with Logging {
  info("Main")
  trace("LOG TEST")

  val producer = new KafkaProducer[String,String](propsFromConfig(conf.getConfig("kafka_producer")))
  val topic = conf.getString("topic.name")
  val msgCount = conf.getInt("topic.message_count")
  (1 to msgCount).foreach(i => {
    producer.send(new ProducerRecord[String,String](topic,i.toString,i.toString))
  })
  producer.close()
  info("Done")

  /**
    * propsFromConfig - Convert a typesafe config object to a Java properties object.
    */
  private def propsFromConfig(config: Config): Properties = {
    import scala.collection.JavaConverters._

    val props = new Properties()

    val map: Map[String, Object] = config.entrySet().asScala.map({ entry =>
      entry.getKey -> entry.getValue.unwrapped()
    })(collection.breakOut)

    props.putAll(map.asJava)
    props
  }
}
