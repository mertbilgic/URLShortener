package com.urlShortener

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.urlShortener.routes.ShortenerRoute
import com.urlShortener.services.ShortenerServiceImpl

object Server extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materialize: Materializer = Materializer(system)

  val shortenerRoute = new ShortenerRoute(new ShortenerServiceImpl())

  val serviceRoutes: Route = shortenerRoute.route

  HttpService.run(serviceRoutes)

}
