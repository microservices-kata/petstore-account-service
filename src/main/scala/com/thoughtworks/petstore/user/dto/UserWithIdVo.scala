package com.thoughtworks.petstore.user.dto

import scala.beans.BeanProperty

case class UserWithIdVo(@BeanProperty userId: Long,
                        @BeanProperty name: String,
                        @BeanProperty gender: String,
                        @BeanProperty email: String,
                        @BeanProperty phone: String) {
  def this() = this(0, null, null, null, null)
}
