package com.urlShortener.util

import com.urlShortener._Components
import com.urlShortener.config.AppConfig.{httpInterface, httpPort}

import java.net.{MalformedURLException, URL}
import scala.util.{Failure, Success}

object Util extends _Components{
  def getHostUrl(): String = s"http://$httpInterface:$httpPort/"

  def createShortUrl(urlId: Int): String = {
    val hash = hashids.encode(urlId)
    s"${getHostUrl}${hash}/"
  }

  def checkUrlValid(url: String) = {
    try {
      Success(new URL(url))
    }
    catch {
      case exception: MalformedURLException => Failure(exception)
    }
  }

}
