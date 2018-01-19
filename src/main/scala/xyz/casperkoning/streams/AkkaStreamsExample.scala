package xyz.casperkoning.streams

import scala.concurrent._
import scala.concurrent.duration._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import com.typesafe.scalalogging.LazyLogging
import xyz.casperkoning.config._
import xyz.casperkoning.metrics._

case object Tick

case object Tick2

class AkkaStreamsExample(settings: Settings)
  (implicit actorSystem: ActorSystem, ec: ExecutionContext, materializer: Materializer)
  extends LazyLogging {

  var currentNumberOfElements = 1
  var currentSleepTime = 100

  var killSwitch: Option[KillSwitch] = None

  val source = Source
    .tick(0.seconds, 1.second, Tick)
    .alsoTo(Metrics.startOfSourceCounter())
    .flatMapConcat { _ => Source.repeat(Tick2).take(numberOfElements) }
    .alsoTo(Metrics.repeatCounter())
    .batch(max = 1000, seed = i => i :: Nil) { (acc, next) => next :: acc }
    .alsoTo(Metrics.batchSizeHistogram())
    .alsoTo(Metrics.batchCounter())
    .mapAsync(1) { a => Future { Thread.sleep(sleepTime) ; a }}
    .alsoTo(Metrics.finalResultCounter())

  def start() = {
    val (ks, done) = source.viaMat(KillSwitches.single)(Keep.right)
      .toMat(Sink.ignore)(Keep.both)
      .run()

    killSwitch = Some(ks)

    done.onComplete {
      case _ => logger.info("Stream is done")
    }
  }

  def stop() = {
    killSwitch.foreach(_.shutdown())
  }

  def numberOfElements = currentNumberOfElements
  def sleepTime = currentSleepTime
}
