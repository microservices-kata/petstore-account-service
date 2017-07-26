package com.thoughtworks.petstore.user.exception

case class UserNotExistsException(msg: String) extends RuntimeException(msg)
