package net.martinprobson.kafka.consumer

import cats.effect.IO
import com.typesafe.config.{Config, ConfigFactory}

import java.util.Properties

object Configuration {

  val config: IO[Config] = IO(ConfigFactory.load())

  /** Get the kafka consumer properties from configuration.
    * @return
    *   A [[java.util.Properties]] object containing consumer properties to pass to the kafka
    *   consumer.
    */
  def consumerProperties: IO[Properties] = for {
    c <- config
    p <- Util.propsFromConfig(c.getConfig("kafka_consumer"))
  } yield p
}
