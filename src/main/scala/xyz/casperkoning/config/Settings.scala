package xyz.casperkoning.config

import com.typesafe.config._

case class Settings(config: Config = ConfigFactory.load()) {
  object Http {
    private val httpConfig = config.getConfig("http")
    val interface = httpConfig.getString("interface")
    val port = httpConfig.getInt("port")
  }
}
