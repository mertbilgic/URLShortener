package com.urlShortener.persistence

import akka.http.scaladsl.server.Directives._

object Model {

  case class Urls(id: Int, created: String, originalUrl: String, click: Int)

  case class Url(url: String)

  object ModelParams {

    val urlParams = parameters(
      "url".as[String],
    ).as(Url.apply _)
  }

}
