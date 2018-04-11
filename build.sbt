lazy val commonSettings = Seq(
  name := "akka-streams-metrics",
  organization := "xyz.casperkoning",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.12.4"
)

lazy val app = (project in file(".")).enablePlugins(AspectJWeaver)
  .settings(commonSettings: _*)
  .settings(dockerUpdateLatest := true)

libraryDependencies ++= {
  // compile
  val akkaHttp          = "com.typesafe.akka"          %% "akka-http"            % "10.1.0"
  val akkaStreams       = "com.typesafe.akka"          %% "akka-stream"          % "2.5.11"
  val kamon             = "io.kamon"                   %% "kamon-core"           % "1.0.0"
  val kamonScalaFuture  = "io.kamon"                   %% "kamon-scala-future"   % "1.0.0"
  val kamonPrometheus   = "io.kamon"                   %% "kamon-prometheus"     % "1.0.0"
  val scalaLogging      = "com.typesafe.scala-logging" %% "scala-logging"        % "3.8.0"

  // test
  val scalaTest         = "org.scalatest"     %% "scalatest"            % "3.0.5" % Test

  // runtime
  val akkaSlf4j         = "com.typesafe.akka" %% "akka-slf4j"           % "2.5.11" % Runtime
  val logback           = "ch.qos.logback"    %  "logback-classic"      % "1.2.3" % Runtime

  Seq(
    akkaHttp,
    akkaStreams,
    kamon,
    kamonScalaFuture,
    kamonPrometheus,
    scalaTest,
    akkaSlf4j,
    scalaLogging,
    logback
  )
}
