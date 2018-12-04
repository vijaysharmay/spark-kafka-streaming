name := "spark-kafka-streaming"

version := "0.1"

scalaVersion := "2.12.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-actor"            % "2.5.18",
  "com.typesafe.akka"          %% "akka-stream"           % "2.5.18",
  "com.typesafe.akka"          %% "akka-http"             % "10.1.5",
  "com.typesafe.akka"          %% "akka-http-spray-json"  % "10.1.5",
  "ch.qos.logback"              % "logback-classic"       % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"         % "3.9.0",
  "com.danielasfregola"        %% "twitter4s"             % "5.5",
  "com.lightbend"              %% "kafka-streams-scala"   % "0.2.1",
  "org.apache.kafka"           %% "kafka"                 % "2.1.0",
  "org.apache.kafka"            % "kafka-clients"         % "2.1.0",
)

assemblyJarName in assembly := "spark-kafka-streaming.jar"
test in assembly := {}
mainClass in assembly := Some("com.streaming.Server")
scalaVersion in ThisBuild := "2.12.6"
assemblyMergeStrategy in assembly := {
  case "reference.conf" => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}