package com.thoughtworks.petstore.user.dto

import scala.beans.BeanProperty

case class UserVo(@BeanProperty name: String,
                @BeanProperty password: String,
                @BeanProperty gender: String,
                @BeanProperty email: String,
                @BeanProperty phone: String) {
  def this() = this(null, null, null, null, null)
}
