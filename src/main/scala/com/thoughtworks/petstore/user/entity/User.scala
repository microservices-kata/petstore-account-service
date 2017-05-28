package com.thoughtworks.petstore.user.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
//import javax.persistence.GeneratedValue
import scala.beans.BeanProperty
import java.lang.Long


object User {
  def apply(userId: Long, name: String, password: String, phone: String): User = {
    new User(new ObjectId, userId, name, password, phone)
  }
}

@Document
case class User(@Id _id: ObjectId,
                @BeanProperty userId: Long,
                @BeanProperty name: String,
                @BeanProperty password: String,
                @BeanProperty phone: String) {
  def this() = this(null, null, null, null, null)
}
