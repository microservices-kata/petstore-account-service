package com.thoughtworks.petstore.user.service

import java.util.List

import com.google.common.collect.ImmutableList
import com.thoughtworks.petstore.user.dto.UserVo
import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.exception.{UserExistsException, UserNameTooLongException, UserNotExistsException, UserPasswordIncorrectException}
import com.thoughtworks.petstore.user.repository.{LastUserIdRepository, UserRepository}
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.mockito.{InjectMocks, Mock, MockitoAnnotations}
import org.mockito.Mockito._

class UserServiceTest extends FunSuite with BeforeAndAfterEach {

  @InjectMocks
  private var userService: UserService = _
  @Mock
  private var userRepository: UserRepository = _
  @Mock
  private var lastUserIdRepository: LastUserIdRepository = _

  override def beforeEach() {
    MockitoAnnotations.initMocks(this)
  }

  test("should able to create valid user name") {
    val user: User = User(1L, "fan", "1234", "Male", "flin@tw.com", "123456789")
    val userVo: UserVo = UserVo("fan", "1234", "Male", "flin@tw.com", "123456789")
    when(lastUserIdRepository.getNextUserId()).thenReturn(1L)
    userService.createUser(userVo)
    verify(userRepository).createUser(user)
  }

  test("should throw exception when create user name longer than 20 chars") {
    val userVo: UserVo = UserVo("fan456789012345678901", "1234", "Male", "flin@tw.com", "123456789")
    assertThrows[UserNameTooLongException] {
      userService.createUser(userVo)
    }
  }

  test("should throw exception when create user name already exist") {
    val user: User = User(1L, "fan", "1234", "Male", "flin@tw.com", "123456789")
    val userVo: UserVo = UserVo("fan", "1234", "Male", "flin@tw.com", "123456789")
    when(userRepository.findAllUserByName("fan")).thenReturn(ImmutableList.of(user))
    assertThrows[UserExistsException] {
      userService.createUser(userVo)
    }
  }

  test("should able to update valid user name") {
    val user: User = User(1L, "fan", "1234", "Male", "flin@tw.com", "123456789")
    val userVo: UserVo = UserVo("fan", "1234", "Male", "flin@tw.com", "123456789")
    userService.updateUser(1L, userVo)
    verify(userRepository).updateUser(user)
  }

  test("should throw exception when update user name longer than 20 chars") {
    val userVo: UserVo = UserVo("fan456789012345678901", "1234", "Male", "flin@tw.com", "123456789")
    assertThrows[UserNameTooLongException] {
      userService.updateUser(1L, userVo)
    }
  }

  test("should able to get user info when user to login has correct password") {
    val user: User = User(1L, "fan", "1234", "Male", "flin@tw.com", "123456789")
    when(userRepository.findUserByName("fan")).thenReturn(user)
    val resUser = userService.credentialMatch("fan", "1234")
    assert(resUser.name == "fan")
  }

  test("should throw exception when user to login not exist") {
    when(userRepository.findUserByName("fan")).thenReturn(null)
    assertThrows[UserNotExistsException] {
      userService.credentialMatch("fan", "1234")
    }
  }

  test("should throw exception when user to login has incorrect password") {
    val user: User = User(1L, "fan", "1234", "Male", "flin@tw.com", "123456789")
    when(userRepository.findUserByName("fan")).thenReturn(user)
    assertThrows[UserPasswordIncorrectException] {
      userService.credentialMatch("fan", "4321")
    }
  }

}
