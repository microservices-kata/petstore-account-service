package com.thoughtworks.petstore.user.exception

case class UserPasswordIncorrectException(msg: String) extends RuntimeException(msg)
