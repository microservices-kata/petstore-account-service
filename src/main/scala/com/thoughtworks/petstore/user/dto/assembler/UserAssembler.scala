package com.thoughtworks.petstore.user.dto.assembler

import com.thoughtworks.petstore.user.dto.{UserVo, UserWithIdVo}
import com.thoughtworks.petstore.user.entity.User

object UserAssembler {

  def userVo2UserEntity(userId: Long, userVo: UserVo): User = {
    User(userId, userVo.getName, userVo.password, userVo.gender, userVo.getEmail, userVo.getPhone)
  }

  def userEntity2UserWithIdVo(user: User): UserWithIdVo = {
    UserWithIdVo(user.getUserId, user.getName, user.getGender, user.getEmail, user.getPhone)
  }

}
