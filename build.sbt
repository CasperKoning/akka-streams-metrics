lazy val commonSettings = Seq(
  name := "akka-streams-metrics",
  organization := "xyz.casperkoning",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.12.3"
)

lazy val app = (project in file(".")).enablePlugins(JavaAppPackaging, AspectJWeaver)
  .settings(commonSettings: _*)
  .settings( // heroku settings
    herokuAppName in Compile := sys.props.getOrElse("heroku_name", "secret"),
    herokuJdkVersion in Compile := "1.8",
    herokuConfigVars in Compile := Map(
    ),
    herokuProcessTypes in Compile := Map(
      "web" -> s"target/universal/stage/bin/akka-streams-metrics -Dhttp.port=$${PORT}"
    )
  )

libraryDependencies ++= {
  // compile
  val akkaHttp          = "com.typesafe.akka"          %% "akka-http"            % "10.0.11"
  val akkaStreams       = "com.typesafe.akka"          %% "akka-stream"          % "2.5.9"
  val kamon             = "io.kamon"                   %% "kamon-core"           % "0.6.6"
  val kamonSystem       = "io.kamon"                   %% "kamon-system-metrics" % "0.6.6"
  val kamonAkkaHttp     = "io.kamon"                   %% "kamon-akka-http"      % "0.6.6"
  val kamonInfluxDb     = "io.kamon"                   %% "kamon-influxdb"       % "0.6.6"
  val scalaLogging      = "com.typesafe.scala-logging" %% "scala-logging"        % "3.7.2"

  // test
  val scalaTest         = "org.scalatest"     %% "scalatest"            % "3.0.4" % Test

  // runtime
  val akkaSlf4j         = "com.typesafe.akka" %% "akka-slf4j"           % "2.5.9" % Runtime
  val logback           = "ch.qos.logback"    %  "logback-classic"      % "1.2.3" % Runtime

  Seq(
    akkaHttp,
    akkaStreams,
    kamon,
    kamonSystem,
    kamonAkkaHttp,
    kamonInfluxDb,
    scalaTest,
    akkaSlf4j,
    scalaLogging,
    logback
  )
}
