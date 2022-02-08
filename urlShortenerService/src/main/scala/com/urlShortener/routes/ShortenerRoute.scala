package com.urlShortener.routes

import akka.http.scaladsl.model.{HttpResponse, StatusCodes, Uri}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.urlShortener.Server.system.dispatcher
import com.urlShortener.persistence.Model._
import com.urlShortener.services.ShortenerService
import org.slf4j.{Logger, LoggerFactory}

import scala.util.{Failure, Success}

class ShortenerRoute(shortenerService: ShortenerService) {
  val logger: Logger = LoggerFactory.getLogger(getClass.getName)
  val shortenerAPI = "shortener"

  val route: Route =
    pathPrefix(shortenerAPI){
      concat(
        pathEndOrSingleSlash {
          concat(
            (get & entity(as[Url])){ data =>
              complete(shortenerService.redirectUrl(data))
            },
            (post & entity(as[Url])) { data =>
              logger.info(s"POST request for create url ${data.url}")
              complete(shortenerService.create(data))
            }
          )
        }
      )
    }

}
