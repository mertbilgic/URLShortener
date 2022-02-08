package com.urlShortener.persistence

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.urlShortener.services.ErrorResponse
import spray.json.DefaultJsonProtocol

object Model extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val urlsJsonFormat = jsonFormat4(Urls)
  implicit val urlJsonFormat = jsonFormat1(Url)
  implicit val errorJsonFormat = jsonFormat2(ErrorResponse)

  case class Urls(id: Int, created: String, originalUrl: String, click: Int)
  case class Url(url: String)

}
