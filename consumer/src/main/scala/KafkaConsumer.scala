import java.time.Duration
import java.util.regex.Pattern
import grizzled.slf4j.Logging
import Util.propsFromConfig
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object KafkaConsumer extends App with Configured with Logging {

  val consumer = new KafkaConsumer[String, String](propsFromConfig(conf.getConfig("kafka_consumer")))
  consumer.subscribe(java.util.Arrays.asList("Martin6"))
  var records: ConsumerRecords[String,String] = null
  records = consumer.poll(Duration.ofMillis(1000))
  records.forEach(r => {
    println(s"offset: ${r.offset()}, key: ${r.key}, Value: ${r.value}")
  })
  var count = records.count()
  while (!records.isEmpty) {
    records = consumer.poll(Duration.ofMillis(1000))
    records.forEach(r => {
      println(s"offset: ${r.offset()}, key: ${r.key}, Value: ${r.value}")
    })
    count = count + records.count()
  }
  consumer.close
  println(s"Total count = $count")
}
