package com.thoughtworks.petstore.user.controller

import io.swagger.annotations.{ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation._
import com.thoughtworks.petstore.user.entity.User
import com.thoughtworks.petstore.user.repository.UserRepository

import scala.beans.BeanProperty

@RestController
@RequestMapping (value = Array ("/api/users") )
class UserController @Autowired() (private val userRepository: UserRepository) {

  @Autowired
  var client: DiscoveryClient = _

  @Value("${demo.env}")
  @BeanProperty
  var lang: String = _

  @ApiOperation(value = "Get user info")
  @RequestMapping(value = Array("/{userId}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getUser(@ApiParam(required = true, name = "userId", value = "User Id") @PathVariable userId: Long) = {
    userRepository.findByUserId(userId).get(0)
  }

  @ApiOperation(value = "Create new user")
  @RequestMapping(value = Array(""), method = Array(RequestMethod.POST))
  def hello(@RequestBody user: User): User = {
    userRepository.save(user)
  }

}
