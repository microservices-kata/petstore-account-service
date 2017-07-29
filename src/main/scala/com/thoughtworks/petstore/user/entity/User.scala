package com.thoughtworks.petstore.user.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import scala.beans.BeanProperty


object User {
  def apply(userId: Long, name: String, password: String, gender: String,
            email: String, phone: String): User = {
    new User(new ObjectId, userId, name, password, gender, email, phone)
  }
}

@Document
case class User(@Id _id: ObjectId,
                @BeanProperty userId: Long,
                @BeanProperty name: String,
                @BeanProperty password: String,
                @BeanProperty gender: String,
                @BeanProperty email: String,
                @BeanProperty phone: String) {
  def this() = this(null, 0, null, null, null, null, null)

  override def equals(user: Any): Boolean = {
    user match {
      case u: User => userId.equals(u.userId) &&
        name.equals(u.name) &&
        password.equals(u.password) &&
        gender.equals(u.gender) &&
        email.equals(u.email) &&
        phone.equals(u.phone)
      case _ => false
    }
  }
}
