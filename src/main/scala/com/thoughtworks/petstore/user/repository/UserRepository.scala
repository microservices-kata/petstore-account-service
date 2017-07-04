package com.thoughtworks.petstore.user.repository

import java.util

import com.thoughtworks.petstore.user.entity.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.stereotype.Component

@Component
class UserRepository {

  private val LOG = LoggerFactory.getLogger(classOf[UserRepository])

  @Autowired
  var mongoOps: MongoOperations = _

  def findUserById(userId: Long): User =
    mongoOps.findOne(query(where("userId").is(userId)), classOf[User])

  def findAllUserByName(userName: String): util.List[User] =
    mongoOps.find(query(where("name").is(userName)), classOf[User])

  def findUserByName(userName: String): User =
    mongoOps.findOne(query(where("name").is(userName)), classOf[User])

  def createUser(user: User): User = {
    val newUser = User(user.userId, user.name, user.password, user.gender, user.email, user.phone)
    mongoOps.insert(newUser)
    newUser
  }

  def updateUser(user: User): User = {
    val oriUser = mongoOps.findOne(query(where("userId").is(user.userId)), classOf[User])
    val newUser = new User(oriUser._id, user.userId, user.name, user.password, user.gender, user.email, user.phone)
    mongoOps.save(newUser)
    newUser
  }

  def removeUser(user: User): Int = mongoOps.remove(query(where("userId").is(user.userId))).getN
}
