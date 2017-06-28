package com.thoughtworks.petstore.user.service

import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.repository.{LastUserIdRepository, UserRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService {

  @Autowired
  var userRepository: UserRepository = _
  @Autowired
  var lastUserIdRepository: LastUserIdRepository = _

  def createUser(user: User): User = {
    val userId = lastUserIdRepository.getNextUserId()
    userRepository.createUser(User(userId, user.getName, user.password, user.gender,
      user.getEmail, user.getPhone))
  }

  def updateUser(user: User): User = userRepository.updateUser(user)

  def findUserById(userId: Long): User = userRepository.findUserById(userId)
}