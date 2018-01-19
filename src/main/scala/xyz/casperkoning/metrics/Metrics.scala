package xyz.casperkoning.metrics

import akka.stream.scaladsl.{Flow, Sink}
import kamon.Kamon

object Metrics {


  private val startCounter = Kamon.metrics.counter("start-of-source")
  private val repeatedCounter = Kamon.metrics.counter("repeated-elements")
  private val batchHistogram = Kamon.metrics.histogram("batch-size")
  private val batchesCounter = Kamon.metrics.counter("batches")
  private val finalResultsCounter = Kamon.metrics.counter("final-results")

  def startOfSourceCounter[T]() = Sink.foreach { _: T => startCounter.increment() }
  def repeatCounter[T]() = Sink.foreach { _: T => repeatedCounter.increment() }
  def batchSizeHistogram[T]() = Sink.foreach { batch: Seq[T] => batchHistogram.record(batch.size)}
  def batchCounter[T]() = Sink.foreach { _: T => batchesCounter.increment()}
  def finalResultCounter[T]() = Sink.foreach { _: T => finalResultsCounter.increment()}

}
