enablePlugins(PlayScala)

name := "play-kafka-example"

version := "0.1"

scalaVersion := "2.12.8"

// Library dependencies
libraryDependencies ++= Seq(
  guice,
  "com.typesafe.akka" %% "akka-stream-kafka" % "1.0-RC1"
)
