package com.thoughtworks.petstore.user.dto

import scala.beans.BeanProperty

case class ExceptionVo(@BeanProperty code: Int,
                       @BeanProperty message: String) {
  def this() = this(0, null)
}
