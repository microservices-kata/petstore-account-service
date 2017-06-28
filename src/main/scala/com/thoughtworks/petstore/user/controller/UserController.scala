package com.thoughtworks.petstore.user.controller

import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.service.UserService
import io.swagger.annotations.{ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping (value = Array ("/api/users") )
class UserController {

  @Autowired
  var userService: UserService = _

  @ApiOperation(value = "Get user info")
  @RequestMapping(value = Array("/{userId}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getUser(@ApiParam(required = true, name = "userId", value = "User Id")
              @PathVariable userId: Long): User = {
    userService.findUserById(userId)
  }

  @ApiOperation(value = "Update user info")
  @RequestMapping(value = Array(""), method = Array(RequestMethod.PUT))
  def updateUser(@RequestBody user: User): User = {
    userService.updateUser(user)
  }

  @ApiOperation(value = "Create new user")
  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def createUser(@RequestBody user: User): User = {
    userService.createUser(user)
  }

}
