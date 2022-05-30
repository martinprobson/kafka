package net.martinprobson.kafka.consumer

import cats.effect.IO
import com.typesafe.config.Config

import java.util.Properties

object Util {

  /** propsFromConfig - Convert a typesafe config object to a Java properties object.
    */
  def propsFromConfig(config: Config): IO[Properties] = IO {
    val props: Properties = new Properties()

    config.entrySet().forEach(c => props.put(c.getKey, c.getValue.unwrapped()))
    props
  }

}
