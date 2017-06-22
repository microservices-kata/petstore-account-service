package com.thoughtworks.petstore.user.repository

import com.mongodb.Mongo
import com.thoughtworks.petstore.user.entity.{LastUserId, User}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.{FindAndModifyOptions, MongoOperations}
import org.springframework.stereotype.Component

@Component
class UserRepository {

  private val LOG = LoggerFactory.getLogger(classOf[UserRepository])

  @Autowired
  var mongoOps: MongoOperations = _
  @Autowired
  var mongo: Mongo = _

  def findUserById(userId: Long): User = mongoOps.findOne(query(where("userId").is(userId)), classOf[User])

  def createUser(user: User): User = {
    val options = FindAndModifyOptions.options().returnNew(true)
    val lastUserId = mongoOps.findAndModify(query(where("lastId").exists(true)),
      new Update().inc("lastId", 1), options, classOf[LastUserId])
    val lastId = lastUserId match {
      case null => mongoOps.insert(LastUserId(1)); 1
      case _ => lastUserId.lastId
    }
    val newUser = User(lastId, user.name, user.password, user.email, user.phone)
    mongoOps.insert(newUser)
    newUser
  }

  def updateUser(user: User): User = {
    val oriUser = mongoOps.findOne(query(where("userId").is(user.userId)), classOf[User])
    val newUser = new User(oriUser._id, user.userId, user.name, user.password, user.email, user.phone)
    mongoOps.save(newUser)
    newUser
  }
}
