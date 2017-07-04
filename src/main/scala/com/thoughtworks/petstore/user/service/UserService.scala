package com.thoughtworks.petstore.user.service

import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.repository.{LastUserIdRepository, UserRepository}
import com.thoughtworks.petstore.user.dto.UserVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService {

  @Autowired
  var userRepository: UserRepository = _
  @Autowired
  var lastUserIdRepository: LastUserIdRepository = _

  def createUser(user: UserVo): User = {
    val userId = lastUserIdRepository.getNextUserId()
    userRepository.createUser(User(userId, user.getName, user.password, user.gender,
      user.getEmail, user.getPhone))
  }

  def updateUser(userId: Long, user: UserVo): User = {
    userRepository.updateUser(User(userId, user.getName, user.password, user.gender,
      user.getEmail, user.getPhone))
  }

  def findUserById(userId: Long): User = userRepository.findUserById(userId)

  def findUserByName(userName: String): User = userRepository.findUserByName(userName)
}