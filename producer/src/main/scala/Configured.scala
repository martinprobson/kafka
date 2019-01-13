import com.typesafe.config
import com.typesafe.config.ConfigFactory

/**
  * Configured - A holder for a (typesafe) config object.
  * Mixin this trait to access config information.
  * @see [[https://github.com/lightbend/config#using-the-library typesafe]] for more info on using
  *     the library.
  */
trait Configured {
  lazy val conf: config.Config  = ConfigFactory.load
}

