package com.thoughtworks.petstore.user.repository

import java.util.List

import org.bson.types.ObjectId
import com.thoughtworks.petstore.user.entity.User
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
trait UserRepository extends MongoRepository[User, ObjectId] {

  def findByUserId(userId: Long): List[User]
}
