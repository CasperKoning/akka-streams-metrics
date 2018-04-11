package xyz.casperkoning.metrics

import akka.stream.scaladsl.Sink
import kamon.Kamon
import kamon.trace.Tracer

object Metrics {

  private val startCounter = Kamon.counter("start-of-source")
  private val emittedCounter = Kamon.counter("elements-emitted")
  private val batchHistogram = Kamon.histogram("batch-size")
  private val finalResultsCounter = Kamon.counter("batch-processed")

  def startOfSourceCounter[T]() = Sink.foreach { _: T => startCounter.increment() }
  def emitCounter[T]() = Sink.foreach { _  : T => emittedCounter.increment() }
  def batchSizeHistogram[T]() = Sink.foreach { batch: Seq[T] => {
      batchHistogram.record(batch.size)
    }
  }
  def batchesProcessedCounter[T]() = Sink.foreach { _: T => finalResultsCounter.increment()}

  def time[T](spanName: String)(body: => T): T = {
    val span = Kamon.buildSpan(spanName).start()
    val t = body
    span.finish()
    t
  }

}
