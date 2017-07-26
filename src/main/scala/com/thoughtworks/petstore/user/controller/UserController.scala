package com.thoughtworks.petstore.user.controller

import com.thoughtworks.petstore.user.dto.assembler.UserAssembler
import com.thoughtworks.petstore.user.dto.{ExceptionVo, UserVo, UserWithIdVo}
import com.thoughtworks.petstore.user.exception._
import com.thoughtworks.petstore.user.service.UserService
import io.swagger.annotations.{ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping (value = Array ("/api/users"))
class UserController {

  @Autowired
  private var userService: UserService = _

  @ApiOperation(value = "Check whether user's password match")
  @RequestMapping(value = Array("/authentication"), method = Array(RequestMethod.GET))
  @ResponseBody
  def matchUserCredential(@ApiParam(required = true, name = "name", value = "User Name") @RequestParam name: String,
                    @ApiParam(required = true, name = "pass", value = "User Password") @RequestParam pass: String)
    : UserWithIdVo = {
    UserAssembler.userEntity2UserWithIdVo(userService.credentialMatch(name, pass))
  }

  @ApiOperation(value = "Get user info")
  @RequestMapping(value = Array("/{userId}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getUserById(@ApiParam(required = true, name = "userId", value = "User Id") @PathVariable userId: Long)
    : UserWithIdVo = {
    UserAssembler.userEntity2UserWithIdVo(userService.findUserById(userId))
  }

  @ApiOperation(value = "Update user info")
  @RequestMapping(value = Array("/{userId}"), method = Array(RequestMethod.PUT))
  def updateUser(@ApiParam(required = true, name = "userId", value = "User Id") @PathVariable userId: Long,
                 @RequestBody user: UserVo): UserWithIdVo = {
    UserAssembler.userEntity2UserWithIdVo(userService.updateUser(userId, user))
  }

  @ApiOperation(value = "Create new user")
  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def createUser(@RequestBody user: UserVo): UserWithIdVo = {
    UserAssembler.userEntity2UserWithIdVo(userService.createUser(user))
  }

  @ExceptionHandler(Array(classOf[UserExistsException]))
  @ResponseStatus(HttpStatus.CONFLICT)
  def handleUserAlreadyExistException(e: RuntimeException): ExceptionVo = ExceptionVo(409, e.getMessage)

  @ExceptionHandler(Array(classOf[UserNameTooLongException], classOf[UserNotExistsException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleUserNameTooLongException(e: RuntimeException): ExceptionVo = ExceptionVo(400, e.getMessage)

  @ExceptionHandler(Array(classOf[UserPasswordIncorrectException]))
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  def handleUserPasswordIncorrectException(e: RuntimeException): ExceptionVo = ExceptionVo(406, e.getMessage)
}
