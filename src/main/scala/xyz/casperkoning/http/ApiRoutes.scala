package xyz.casperkoning.http

import scala.concurrent._
import scala.util._

import akka.http.scaladsl.server.Directives._

import kamon._

trait ApiRoutes {
  private val counter = Kamon.metrics.counter("healths")

  def routes()(implicit ec: ExecutionContext) = 
    path("health") {
     get {
      complete {
        counter.increment()    
        "I'm healthy!!!"
      }
     }
    }
}
