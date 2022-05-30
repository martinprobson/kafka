import java.util.Properties
import java.util.concurrent.TimeUnit
import com.typesafe.config.Config
import grizzled.slf4j.Logging
import org.apache.commons.collections4.MapUtils
import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerRecord}

object Util {
  /**
    * propsFromConfig - Convert a typesafe config object to a Java properties object.
    */
  def propsFromConfig(config: Config): Properties = {
    import scala.collection.JavaConverters._

    val map: Map[String, Object] = config
            .entrySet()
            .asScala
            .map({ entry =>
              entry.getKey -> entry.getValue.unwrapped()
            })(collection.breakOut)

    MapUtils.toProperties(map.asJava)
  }

}
