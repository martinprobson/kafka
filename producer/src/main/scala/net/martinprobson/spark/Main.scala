package net.martinprobson.spark

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, NewTopic}
import java.util.Collections
import scala.collection.JavaConverters._
import org.apache.kafka.common.errors.TopicExistsException

object Main extends App with Configured with Logging {
  logger.info("Main")
  logger.trace("LOG TEST")

  private val topic = conf.getString("topic.name")
  private val numPartitions = 100
  private val replicationFactor: Short = 3

  // AdminClient properties
  private val adminClientProps = new Properties()
  adminClientProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getConfig("kafka_producer").getString("bootstrap.servers"))

  // Create AdminClient
  private val adminClient = AdminClient.create(adminClientProps)

  try {
    // Check if topic exists
    val topics = adminClient.listTopics().names().get().asScala
    if (!topics.contains(topic)) {
      logger.info(s"Topic '$topic' does not exist. Creating with $numPartitions partitions and replication factor $replicationFactor.")
      val newTopic = new NewTopic(topic, numPartitions, replicationFactor)
      try {
        adminClient.createTopics(Collections.singletonList(newTopic)).all().get()
        logger.info(s"Topic '$topic' created successfully.")
      } catch {
        case e: java.util.concurrent.ExecutionException =>
          e.getCause match {
            case _: TopicExistsException => logger.warn(s"Topic '$topic' already exists.")
            case _ => throw e
          }
      }
    } else {
      logger.info(s"Topic '$topic' already exists.")
    }
  } finally {
    adminClient.close()
  }

  //TODO Do this in spark in parallel.
  private val producer = new KafkaProducer[String,String](Util.propsFromConfig(conf.getConfig("kafka_producer")))
  private val msgCount = conf.getInt("topic.message_count")
  (1 to msgCount).foreach(i => {
    //producer.send(new ProducerRecord[String,String](topic,i.toString,i.toString))
    producer.send(new ProducerRecord[String,String](topic,i.toString,i.toString))
  })
  producer.close()
  logger.info("Done")

}
