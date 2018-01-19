package xyz.casperkoning.metrics

import akka.stream.scaladsl.Sink
import kamon.Kamon
import kamon.trace.Tracer

object Metrics {


  private val startCounter = Kamon.metrics.counter("start-of-source")
  private val emittedCounter = Kamon.metrics.counter("elements-emitted")
  private val batchHistogram = Kamon.metrics.histogram("batch-size")
  private val finalResultsCounter = Kamon.metrics.counter("batch-processed")

  def startOfSourceCounter[T]() = Sink.foreach { _: T => startCounter.increment() }
  def emitCounter[T]() = Sink.foreach { _  : T => emittedCounter.increment() }
  def batchSizeHistogram[T]() = Sink.foreach { batch: Seq[T] => batchHistogram.record(batch.size)}
  def batchesProcessedCounter[T]() = Sink.foreach { _: T => finalResultsCounter.increment()}

  def time[T](traceName: String)(body: => T): T = Tracer.withNewContext(traceName, autoFinish = true)(body)
}
