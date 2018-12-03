package com.streaming

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContextExecutor


object Server extends App with LazyLogging{

  implicit val system: ActorSystem = ActorSystem("spark-kafka-streaming")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val config = ConfigFactory.load()
  val host = config.getString("http.interface")
  val port = config.getInt("http.port")

  val route =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }

  Http().bindAndHandle(handler = route, interface = host, port = port) map { binding =>
    logger.info(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
    logger.info(s"REST interface could not bind to $host:$port", ex.getMessage)
  }

}

