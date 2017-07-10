package com.thoughtworks.petstore.api

import java.io.IOException

import com.thoughtworks.petstore.user.Application
import com.thoughtworks.petstore.user.dto.UserVo
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.core.StringContains.containsString
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.{Before, FixMethodOrder, Test}
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Array(classOf[Application]))
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class VerifyUserApi {

  @LocalServerPort
  private val port = 0

  @Before def setup(): Unit = {
    RestAssured.baseURI = "http://localhost"
    RestAssured.port = port
    RestAssured.basePath = "/"
  }

  @Test
  @throws[IOException]
  def test_api_00_drop_all_records(): Unit = {
    given.when.delete("/app/users?iKnowItsDangerous=true")
      .then.statusCode(200)
      .body(equalTo("OK"))
  }

    @Test
  @throws[IOException]
  def test_api_01_can_create_new_account(): Unit = {
    val newAccount = UserVo("fan", "1234", "Male", "flin@tw.com", "123456789")
    given.contentType(ContentType.JSON).body(newAccount)
      .when.post("/app/users")
      .then.statusCode(200)
      .body("name", equalTo("fan"))
      .body("gender", equalTo("Male"))
      .body("email", equalTo("flin@tw.com"))
      .body("phone", equalTo("123456789"))
      .body("id", notNullValue())
  }

  @Test
  @throws[IOException]
  def test_api_02_return_400_when_account_name_longer_then_20_chars(): Unit = {
    val newAccount = UserVo("fannnnnnnnnnnnnnnnnnnnnnnnnnn", "1234", "Male", "flin@tw.com", "123456789")
    given.contentType(ContentType.JSON).body(newAccount)
      .when.post("/app/users")
      .then.statusCode(400)
      .body("code", equalTo(400))
      .body("message", containsString("ERROR"))
  }

  @Test
  @throws[IOException]
  def test_api_03_authentication_pass_when_user_name_and_password_match(): Unit = {
    given.when.get("/app/users/authentication?name=fan&pass=1234")
      .then.statusCode(200)
      .body("name", equalTo("fan"))
      .body("id", notNullValue())
  }

  @Test
  @throws[IOException]
  def test_api_04_authentication_fail_with_400_when_user_name_and_password_not_match(): Unit = {
    given.when.get("/app/users/authentication?name=abc&pass=123")
      .then.statusCode(401)
      .body("code", equalTo(401))
      .body("message", equalTo("fail"))
  }

  @Test
  @throws[IOException]
  def test_api_05_can_get_account_info(): Unit = {
    given.when.get("/app/users/1")
      .then.statusCode(200)
      .body("name", equalTo("fan"))
      .body("gender", equalTo("Male"))
      .body("email", equalTo("flin@tw.com"))
      .body("phone", equalTo("123456789"))
      .body("id", notNullValue())
  }

}
