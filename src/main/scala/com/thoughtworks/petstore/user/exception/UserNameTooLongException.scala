package com.thoughtworks.petstore.user.exception

case class UserNameTooLongException(msg: String) extends RuntimeException(msg)
