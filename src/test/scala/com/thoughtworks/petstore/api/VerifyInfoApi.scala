package com.thoughtworks.petstore.api

import io.restassured.RestAssured
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import com.thoughtworks.petstore.user.Application
import java.io.IOException
import io.restassured.RestAssured.given
import org.hamcrest.core.IsEqual.equalTo
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT


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
  def test_api__can__return_default_pets___by_direct_access_json_field(): Unit = {
    given.when.get("/app/info")
      .then().statusCode(200)
      .body("language", equalTo("Scala!"))
  }
}
