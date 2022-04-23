package com.urlShortener.services

import akka.http.scaladsl.model.StatusCodes
import com.urlShortener._Components
import com.urlShortener.persistence.Model._
import com.urlShortener.util.Util.{checkUrlValid, createShortUrl}
import org.slf4j.{Logger, LoggerFactory}

import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class ShortenerServiceImpl extends ShortenerService with _Components {
  private val clickCount = 0
  private val logger: Logger = LoggerFactory.getLogger(getClass.getName)
  private var UrlMap = Map[Long, Urls]()
  private var id = 0

  override def create(urlData: Url): Future[ServiceResponse[Url]] = Future {
    logger.info(s"created [${urlData.url}]")
    checkUrlValid(urlData.url) match {
      case Success(_) =>
        val urls = Urls(id, LocalDateTime.now().toString, urlData.url, clickCount)
        val shortUrl = createShortUrl(id)
        id = id + 1
        Try(UrlMap + (urls.id.toLong -> urls)).map(updated => UrlMap = updated) match {
          case Failure(exception) => Left(ErrorResponse(exception.getMessage, StatusCodes.InternalServerError.intValue))
          case Success(_) => Right(Url(shortUrl))
        }
      case Failure(_) => Left(ErrorResponse("Malformed url", StatusCodes.BadRequest.intValue))
    }
  }

  override def redirectUrl(urlData: Url): Future[ServiceResponse[Url]] = Future {
    checkUrlValid(urlData.url) match {
      case Success(url) =>
        val urlId = Try(hashids.decode(url.getPath.replace("/", "")))
        urlId match {
          case Success(id) if UrlMap.size > 0 =>
            val redirectUrl = UrlMap(id.head).originalUrl
            Right(Url(redirectUrl))
          case _ => Left(ErrorResponse("URL Not Found", StatusCodes.NotFound.intValue))
        }
      case Failure(_) => Left(ErrorResponse("Malformed url", StatusCodes.BadRequest.intValue))
    }
  }

  override def stats(urlData: Url): Future[ServiceResponse[Urls]] = ???

}
