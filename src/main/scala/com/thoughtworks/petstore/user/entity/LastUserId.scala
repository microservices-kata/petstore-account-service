package com.thoughtworks.petstore.user.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

object LastUserId {
  def apply(lastId: Long): LastUserId = {
    new LastUserId(new ObjectId, lastId)
  }
}

@Document
case class LastUserId(@Id _id: ObjectId,
                @BeanProperty lastId: Long) {
  def this() = this(null, 0)
}
