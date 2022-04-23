package com.urlShortener

package object services {

  type ServiceResponse[T] = Either[ErrorResponse, T]

  case class ErrorResponse(message: String, status: Int)


}
