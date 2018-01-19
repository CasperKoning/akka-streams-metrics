package xyz.casperkoning.http

import scala.concurrent._
import akka.http.scaladsl.server.Directives._
import xyz.casperkoning.streams.AkkaStreamsExample

class Api(akkaStreamsExample: AkkaStreamsExample) {
  def routes(implicit ec: ExecutionContext) =
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
