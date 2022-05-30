package net.martinprobson.kafka.consumer

import cats.effect.{ExitCode, IO, IOApp, Resource}
import net.martinprobson.kafka.consumer.Configuration.{config, consumerProperties}
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util.Properties
import scala.jdk.DurationConverters.*

object Main extends IOApp with Logging {

  /** Build a consumer given tne passed Properties using the Resource make method. Pass the consumer
    * to the consume method to start consuming from the topic. The consumer will timeout once
    * timeout interval has passed.
    * @param config
    *   The consumer configuration.
    * @param timeout
    *   The executing consumer will timeout after this interval.
    */
  def consumer(config: IO[Properties], timeout: java.time.Duration): IO[Unit] = {
    val cons = for {
      properties <- Resource.eval[IO, Properties](config)
      c <- Resource.make(acquire(properties))(consumer => release(consumer))
    } yield c
    cons.use { consumer => consume(consumer, timeout) }
  }

  /** Create a consumer.
    * @param properties
    *   Consumer properties.
    * @return
    *   A Consumer wrapped in an IO.
    */
  def acquire(properties: Properties): IO[KafkaConsumer[String, String]] = for {
    consumer <- IO(new KafkaConsumer[String, String](properties))
    _ <- IO(consumer.subscribe(java.util.Arrays.asList("Martin4")))
    _ <- IO(logger.info(s"opening consumer"))
  } yield consumer

  def release(consumer: KafkaConsumer[String, String]): IO[Unit] = {
    IO(logger.info(s"close $consumer")) *> IO(consumer.close())
  }

  /** Main consumer loop.
    * @param consumer
    *   The consumer we are running.
    * @param timeout
    *   Timeout period for poll.
    */
  def consume(consumer: KafkaConsumer[String, String], timeout: java.time.Duration): IO[Unit] =
    for {
      records <- IO.blocking(consumer.poll(timeout))
      // Normally the actual processing of the records would go here.
      _ <- IO(records.forEach(r => {
        logger.info(s"""offset: ${r.offset()},
                       | key: ${r.key()},
                       | value: ${r.value()} ${Thread.currentThread().getName}""".stripMargin)
      }))
      _ <- consume(consumer, timeout)
    } yield ()

  /** Configure <code>cats-consumer.num_of_consumers</code> consumers and run them in parallel until
    * <code>cat-consumer.consumer-timeout</code>, shutdown the main thread after
    * <code>cats-consumer.shutdown-after</code>.
    * @param args
    *   Command line args (not used).
    * @return
    *   ExitCode.Success
    */
  override def run(args: List[String]): IO[ExitCode] = (for {
    cfg <- config
    _ <- consumer(consumerProperties, cfg.getDuration("cats-consumer.poll-duration"))
      .timeout(cfg.getDuration("cats-consumer.consumer-timeout").toScala)
      .start
      .replicateA(cfg.getInt("cats-consumer.num_of_consumers"))
    _ <- IO.sleep(cfg.getDuration("cats-consumer.shutdown-after").toScala)
  } yield ()).as(ExitCode.Success)
}
