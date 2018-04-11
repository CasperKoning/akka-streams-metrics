package xyz.casperkoning

import scala.concurrent._
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import xyz.casperkoning.config._
import xyz.casperkoning.http._
import kamon._
import xyz.casperkoning.streams.AkkaStreamsExample

object Main extends App {
  Kamon.loadReportersFromConfig()

  implicit val config = ConfigFactory.load()
  implicit val system = ActorSystem("akka-streams-metrics", config)
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val settings = Settings(config)

  val akkaStreamsExample = new AkkaStreamsExample(settings)
  akkaStreamsExample.start()

  val api = new Api(akkaStreamsExample)

  val service = new HttpServer(settings, api)
  service.start()

  Runtime.getRuntime.addShutdownHook(new Thread(){
    override def run(): Unit = {
      akkaStreamsExample.stop()
      service.stop()
      materializer.shutdown()
      Await.result(system.terminate(), 10.seconds)
    }
  })
}
