package com.thoughtworks.petstore.user.service

import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.repository.{LastUserIdRepository, UserRepository}
import com.thoughtworks.petstore.user.dto.UserVo
import com.thoughtworks.petstore.user.dto.assembler.UserAssembler
import com.thoughtworks.petstore.user.exception.{UserExistsException, UserNameTooLongException, UserPasswordIncorrectException}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService {

  @Autowired
  var userRepository: UserRepository = _
  @Autowired
  var lastUserIdRepository: LastUserIdRepository = _

  def createUser(userVo: UserVo): User = {
    if (userVo.getName.length > 20) {
      throw UserNameTooLongException("User name should not longer then 20 chars")
    }
    if (! userRepository.findAllUserByName(userVo.name).isEmpty) {
      throw UserExistsException("User already exist")
    }

    val userId = lastUserIdRepository.getNextUserId()
    val user = UserAssembler.userVo2UserEntity(userId, userVo)
    userRepository.createUser(user)
    if (userRepository.findAllUserByName(userVo.name).size() > 1) {  // Double check user name is unique
      userRepository.removeUser(user)
      throw UserExistsException("User creation conflicted")
    }
    user
  }

  def updateUser(userId: Long, userVo: UserVo): User = {
    if (userVo.getName.length > 20) {
      throw UserNameTooLongException("User name should not longer then 20 chars")
    }
    userRepository.updateUser(UserAssembler.userVo2UserEntity(userId, userVo))
  }

  def findUserById(userId: Long): User = userRepository.findUserById(userId)

  def findUserByName(userName: String): User = userRepository.findUserByName(userName)

  def credentialMatch(name: String, pass: String): User = {
    val user = findUserByName(name)
    if (user.getPassword != pass) {
      throw UserPasswordIncorrectException("User name and password not match")
    }
    user
  }

}
