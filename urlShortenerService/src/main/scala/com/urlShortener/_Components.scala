package com.urlShortener

import org.hashids.Hashids

trait _Components {

  lazy val hashids = Hashids("this is my salt :)")

}
