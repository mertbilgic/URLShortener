package com.urlShortener.services

import com.urlShortener.persistence.Model._

import scala.concurrent.Future

trait ShortenerService {

  def create(url: Url): Future[ServiceResponse[Url]]

  def redirectUrl(urlData: Url): Future[ServiceResponse[Url]]

  def stats(urlData: Url): Future[ServiceResponse[Urls]]

}
