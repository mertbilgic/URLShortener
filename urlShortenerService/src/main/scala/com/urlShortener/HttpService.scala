package com.urlShortener

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.urlShortener.config.AppConfig.{httpInterface, httpPort}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class HttpService(routes: Route)(implicit system: ActorSystem, materialize: Materializer) {

  implicit def executor: ExecutionContextExecutor = system.dispatcher
  val logger: LoggingAdapter = Logging(system, getClass)

  val bindingFuture: Future[Http.ServerBinding] =
    Http()
      .newServerAt(httpInterface, httpPort)
      .bind(routes)

  bindingFuture.onComplete {
    case Success(bound) =>
      logger.info(s"Server Started: ${bound.localAddress.getHostString}")
    case Failure(e) =>
      logger.error(s"Server could not start: ${e.getMessage}")
      system.terminate()
  }
}

object HttpService {
  def run(serviceRoutes: Route)(implicit system: ActorSystem, materialize: Materializer) =
    new HttpService(serviceRoutes)
}
