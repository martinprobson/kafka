import java.time.Duration
import java.util.regex.Pattern
import grizzled.slf4j.Logging
import Util.propsFromConfig
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object KafkaConsumer extends App with Configured with Logging {

  val consumer = new KafkaConsumer[String, String](propsFromConfig(conf.getConfig("kafka_consumer")))
  consumer.subscribe(java.util.Arrays.asList("test"))
  var records: ConsumerRecords[String,String] = null
  while (true) {
    records = consumer.poll(Duration.ofMillis(100))
    records.forEach(r => {
      println(s"offset: ${r.offset()}, key: ${r.key}, Value: ${r.value}")
    })
  }
  consumer.close
}
