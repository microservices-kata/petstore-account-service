package com.thoughtworks.petstore.user.controller

import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.service.UserService
import com.thoughtworks.petstore.user.dto.{ExceptionVo, UserVo}
import com.thoughtworks.petstore.user.exception.{UserExistsException, UserNameTooLongException}
import io.swagger.annotations.{ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping (value = Array ("/api/users"))
class UserController {

  @Autowired
  var userService: UserService = _

  @ApiOperation(value = "Get user info via name")
  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getUserByName(@ApiParam(required = true, name = "name", value = "User Name") @RequestParam name: String)
    : User = {
    userService.findUserByName(name)
  }

  @ApiOperation(value = "Get user info")
  @RequestMapping(value = Array("/{userId}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getUserById(@ApiParam(required = true, name = "userId", value = "User Id") @PathVariable userId: Long)
    : User = {
    userService.findUserById(userId)
  }

  @ApiOperation(value = "Update user info")
  @RequestMapping(value = Array("/{userId}"), method = Array(RequestMethod.PUT))
  def updateUser(@ApiParam(required = true, name = "userId", value = "User Id") @PathVariable userId: Long,
                 @RequestBody user: UserVo): User = {
    userService.updateUser(userId, user)
  }

  @ApiOperation(value = "Create new user")
  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def createUser(@RequestBody user: UserVo): User = {
    userService.createUser(user)
  }

  @ExceptionHandler(Array(classOf[UserExistsException]))
  @ResponseStatus(HttpStatus.CONFLICT)
  def handleUserAlreadyExistException(e: RuntimeException): ExceptionVo = {
    ExceptionVo(409, e.getMessage)
  }

  @ExceptionHandler(Array(classOf[UserNameTooLongException]))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleUserNameTooLongException(e: RuntimeException): ExceptionVo = {
    ExceptionVo(400, e.getMessage)
  }
}
