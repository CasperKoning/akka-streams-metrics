package xyz.casperkoning.http

import scala.concurrent._
import akka.http.scaladsl.server.Directives._
import kamon._
import xyz.casperkoning.streams.AkkaStreamsExample

trait ApiRoutes {
  private val counter = Kamon.metrics.counter("healths")

  def routes(akkaStreamsExample: AkkaStreamsExample)(implicit ec: ExecutionContext) =
    path("health") {
     get {
      complete {
        counter.increment()    
        "I'm healthy!!!"
      }
     }
    } ~
   path("config") {
     get {
        parameters('elements.as[Int], 'sleep.as[Int]) { (elements, sleep) =>

          akkaStreamsExample.currentNumberOfElements = elements
          akkaStreamsExample.currentSleepTime = sleep

          complete("done")
        }
     }
   }
}
