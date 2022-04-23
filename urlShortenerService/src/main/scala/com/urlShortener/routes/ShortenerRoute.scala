package com.urlShortener.routes

import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import com.urlShortener.persistence.Model._
import com.urlShortener.services.{ErrorResponse, ShortenerService}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ShortenerRoute(shortenerService: ShortenerService) {
  val logger: Logger = LoggerFactory.getLogger(getClass.getName)
  val shortenerAPI = "shortener"

  val route: Route =
    pathPrefix(shortenerAPI) {
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
