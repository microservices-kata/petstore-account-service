package com.thoughtworks.petstore.user.dto

import scala.beans.BeanProperty

case class CredentialMatchVo(@BeanProperty result: Boolean) {
  def this() = this(false)
}
