package com.thoughtworks.petstore.api

import java.io.IOException

import com.fasterxml.jackson.databind.ObjectMapper
import com.thoughtworks.petstore.config.ConfigServerWithFongoConfiguration
import com.thoughtworks.petstore.user.Application
import com.thoughtworks.petstore.user.dto.UserVo
import com.thoughtworks.petstore.user.entity.User
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.StringContains.containsString
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.{Before, FixMethodOrder, Test}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = RANDOM_PORT,
  classes = Array(classOf[ConfigServerWithFongoConfiguration], classOf[Application]))
@AutoConfigureMockMvc
@TestPropertySource(properties = Array("spring.data.mongodb.database=test"))
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class VerifyUserApi {

  @LocalServerPort
  private val port = 0

  @Autowired
  private var mongoTemplate: MongoTemplate = _

  @Autowired
  private var mockMvc: MockMvc = _

  private val jsonMapper = new ObjectMapper

  @Before def setup(): Unit = {
    RestAssured.baseURI = "http://localhost"
    RestAssured.port = port
    RestAssured.basePath = "/"
  }

  @Test
  @throws[IOException]
  def test_api_can_create_new_account(): Unit = {
    val newAccount = UserVo("fan", "1234", "Male", "flin@tw.com", "123456789")
    given.contentType(ContentType.JSON).body(newAccount)
      .when.post("/api/users")
      .then.statusCode(200)
      .body("name", equalTo("fan"))
      .body("gender", equalTo("Male"))
      .body("email", equalTo("flin@tw.com"))
      .body("phone", equalTo("123456789"))
      .body("userId", notNullValue())
  }

  @Test
  @throws[IOException]
  def test_api_return_400_when_account_name_longer_then_20_chars(): Unit = {
    val newAccount = UserVo("fannnnnnnnnnnnnnnnnnnnnnnnnnn", "1234", "Male", "flin@tw.com", "123456789")
    given.contentType(ContentType.JSON).body(newAccount)
      .when.post("/api/users")
      .then.statusCode(400)
      .body("code", equalTo(400))
      .body("message", containsString("20 chars"))
  }

  @Test
  @throws[IOException]
  def test_api_authentication_pass_when_user_name_and_password_match(): Unit = {
    val userFongo = User(1, "fan", "1234", "Male", "flin@tw.com", "123456789")
    mongoTemplate.createCollection("User")
    mongoTemplate.insert(userFongo)

    given.when.get("/api/users/authentication?name=fan&pass=1234")
      .then.statusCode(200)
      .body("name", equalTo("fan"))
      .body("userId", notNullValue())
  }

  @Test
  @throws[IOException]
  def test_api_authentication_fail_with_406_when_user_name_and_password_not_match(): Unit = {
    val userFongo = User(1, "fan", "1234", "Male", "flin@tw.com", "123456789")
    mongoTemplate.createCollection("User")
    mongoTemplate.insert(userFongo)

    given.when.get("/api/users/authentication?name=fan&pass=4321")
      .then.statusCode(406)
      .body("code", equalTo(406))
      .body("message", containsString("not match"))
  }

  @Test
  @throws[IOException]
  def test_api_fail_with_400_when_requested_user_name_not_exist(): Unit = {
    given.when.get("/api/users/authentication?name=abc&pass=4321")
      .then.statusCode(400)
      .body("code", equalTo(400))
      .body("message", containsString("not exist"))
  }

  @Test
  @throws[IOException]
  def test_api_can_get_account_info(): Unit = {
    val userFongo = User(1, "fan", "1234", "Male", "flin@tw.com", "123456789")
    mongoTemplate.createCollection("User")
    mongoTemplate.insert(userFongo)

    given.when.get("/api/users/1")
      .then.statusCode(200)
      .body("name", equalTo("fan"))
      .body("gender", equalTo("Male"))
      .body("email", equalTo("flin@tw.com"))
      .body("phone", equalTo("123456789"))
      .body("userId", notNullValue())
  }

}
