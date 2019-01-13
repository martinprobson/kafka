import java.util.Properties
import java.util.concurrent.TimeUnit

import com.typesafe.config.Config
import grizzled.slf4j.Logging
import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerRecord}

object Main extends App with Configured with Logging {
  println("Main")
  trace("LOG TEST")

  val producer = new KafkaProducer[String,String](propsFromConfig(conf.getConfig("kafka_producer")))
  (1 to 1000).foreach(i => {
    producer.send(new ProducerRecord[String,String]("test",i.toString,i.toString))
  })
  producer.close()
  println("Done")

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
