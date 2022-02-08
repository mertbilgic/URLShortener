package com.urlShortener.config

import com.typesafe.config.ConfigFactory

object AppConfig {

  val config = ConfigFactory.load()

  val httpInterface = config.getString("http.interface")
  val httpPort = config.getInt("http.port")

}
