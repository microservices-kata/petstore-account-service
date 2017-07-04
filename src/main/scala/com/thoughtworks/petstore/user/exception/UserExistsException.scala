package com.thoughtworks.petstore.user.exception

case class UserExistsException(msg: String) extends RuntimeException(msg)
