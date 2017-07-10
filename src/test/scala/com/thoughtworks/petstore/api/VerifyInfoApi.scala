package com.thoughtworks.petstore.api

import java.io.IOException

import com.thoughtworks.petstore.user.Application
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.core.IsEqual.equalTo
import org.junit.{Before, Test}
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.junit4.SpringRunner


@RunWith(classOf[SpringRunner])
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Array(classOf[Application]))
class VerifyInfoApi {

  @LocalServerPort
  private val port = 0

  @Before def setup(): Unit = {
    RestAssured.baseURI = "http://localhost"
    RestAssured.port = port
    RestAssured.basePath = "/"
  }

  @Test
  @throws[IOException]
  def test_api_can_get_runtime_env_information(): Unit = {
    given.when.get("/app/info")
      .then.statusCode(200)
      .body("language", equalTo("Scala!"))
      .body("environment", equalTo("Test"))
  }
}
