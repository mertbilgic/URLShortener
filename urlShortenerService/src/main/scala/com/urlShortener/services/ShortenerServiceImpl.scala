package com.urlShortener.services

import akka.http.scaladsl.model.{StatusCode, StatusCodes, Uri}
import akka.http.scaladsl.server.Directives.redirect
import com.urlShortener._Components.hashids
import com.urlShortener.persistence.Model._
import com.urlShortener.util.Util.{checkUrlValid, createShortUrl, getHostUrl}
import org.slf4j.{Logger, LoggerFactory}

import java.net.URL
import java.time.LocalDateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class ShortenerServiceImpl extends ShortenerService {
  private val clickCount = 0
  private val logger: Logger = LoggerFactory.getLogger(getClass.getName)
  private var UrlMap = Map[Int, Urls]()
  private var id = 0

  override def create(urlData: Url): Future[ServiceResponse[Url]] = Future {
    logger.info(s"created [${urlData.url}]")
    checkUrlValid(urlData.url) match {
      case Success(_) =>
        val urls = Urls(id, LocalDateTime.now().toString, urlData.url, clickCount)
        val shortUrl = createShortUrl(id)
        id = id + 1
        Try(UrlMap + (urls.id -> urls)).map(updated => UrlMap = updated) match {
          case Failure(exception) => Left(ErrorResponse(exception.getMessage, 0))
          case Success(_) => Right(Url(shortUrl))
        }
      case Failure(_) => Left(ErrorResponse("Malformed url", 10))
    }
  }

  override def redirectUrl(urlData: Url): Future[ServiceResponse[Url]] = Future {
    checkUrlValid(urlData.url) match {
      case Success(url) =>
        val path = url.getPath
        val id = hashids.decode(path.replace("/",""))
        val redirectUrl = UrlMap(id(0).toInt).originalUrl
        Right(Url(redirectUrl))
      case Failure(_) => Left(ErrorResponse("Malformed url", 10))
    }
  }

  override def stats(urlData: Url): Future[ServiceResponse[Urls]] = ???

}
