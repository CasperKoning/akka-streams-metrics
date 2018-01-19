package xyz.casperkoning.http

import scala.concurrent._
import scala.concurrent.duration._
import akka.actor._
import akka.http.scaladsl.Http.ServerBinding
import akka.stream._
import akka.http.scaladsl._
import xyz.casperkoning.config._
import xyz.casperkoning.streams.AkkaStreamsExample

class NumberwangService(val settings: Settings, akkaStreamsExample: AkkaStreamsExample)(implicit actorSystem: ActorSystem, executionContext: ExecutionContext, materializer: Materializer)
  extends ApiRoutes {
  private var serverBinding: Option[ServerBinding] = None

  def start() = {
    Http().bindAndHandle(
      handler = routes(akkaStreamsExample),
      interface = settings.Http.interface,
      port = settings.Http.port
    ) foreach {
      binding => serverBinding = Some(binding)
    }
  }

  def stop() = {
    serverBinding.foreach {b => Await.result(b.unbind(), 10.seconds) }
  }
}
