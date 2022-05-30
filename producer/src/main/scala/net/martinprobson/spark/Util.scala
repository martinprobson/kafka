package net.martinprobson.spark

import java.util.Properties
import com.typesafe.config.Config
import org.apache.commons.collections4.MapUtils

object Util {
  /**
    * propsFromConfig - Convert a typesafe config object to a Java properties object.
    */
  def propsFromConfig(config: Config): Properties = {
    import scala.collection.JavaConverters._

    val map: Map[String, Object] = config.entrySet().asScala.map({ entry =>
      entry.getKey -> entry.getValue.unwrapped()
    })(collection.breakOut)

    MapUtils.toProperties(map.asJava)
  }
}
