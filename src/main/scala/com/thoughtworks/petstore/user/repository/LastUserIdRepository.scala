package com.thoughtworks.petstore.user.repository

import com.thoughtworks.petstore.user.entity.LastUserId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.{FindAndModifyOptions, MongoOperations}
import org.springframework.stereotype.Component

@Component
class LastUserIdRepository {

  private val LOG = LoggerFactory.getLogger(classOf[LastUserIdRepository])

  @Autowired
  private var mongoOps: MongoOperations = _

  def getNextUserId(): Long = {
    val options = FindAndModifyOptions.options().returnNew(true)
    val lastUserId = mongoOps.findAndModify(query(where("lastId").exists(true)),
      new Update().inc("lastId", 1), options, classOf[LastUserId])
    val lastId = lastUserId match {
      case null => mongoOps.insert(LastUserId(1)); 1
      case _ => lastUserId.lastId
    }
    lastId
  }

  def dropMe(): Unit = mongoOps.dropCollection("LastUserId")

}
