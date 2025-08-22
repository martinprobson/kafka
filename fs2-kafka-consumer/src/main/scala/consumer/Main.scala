package consumer

import cats.effect.{IO, IOApp}
import cats.implicits.catsSyntaxParallelTraverse1
import fs2.*
import fs2.kafka.*
import fs2.kafka.consumer.KafkaConsumeChunk.CommitNow
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Main extends IOApp.Simple {

  private def log: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger[IO]

  override def run: IO[Unit] = {
    val consumerSettings = ConsumerSettings[IO, String, String]
      .withAutoOffsetReset(AutoOffsetReset.Earliest)
      .withBootstrapServers("localhost:29092,localhost:39092,localhost:49092")
      .withGroupId("Test4")


    def processRecords(records: Chunk[ConsumerRecord[String,String]]): IO[CommitNow] =
      records.parTraverse(record =>
        log.info(s"Processing record: ${record.key} / ${record.value}")).as(CommitNow)

    KafkaConsumer
      .stream(consumerSettings)
      .subscribeTo("Test3")
      .consumeChunk(processRecords)
  }
}
