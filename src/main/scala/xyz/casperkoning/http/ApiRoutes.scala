package xyz.casperkoning.http

import scala.concurrent._
import scala.util._

import akka.http.scaladsl.server.Directives._

trait ApiRoutes {
  def routes()(implicit ec: ExecutionContext) = 
    path("health") {
     get {
      complete {
        "I'm healthy!!!"
      }
     }
    }
}
