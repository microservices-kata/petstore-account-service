package com.thoughtworks.petstore.contract

import au.com.dius.pact.provider.junit.{PactRunner, Provider, State}
import au.com.dius.pact.provider.junit.loader.PactBroker
import com.thoughtworks.petstore.user.Application
import org.junit.{AfterClass, BeforeClass}
import org.junit.runner.RunWith
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext


@RunWith(classOf[PactRunner])
@Provider("account_provider")
@PactBroker(host = "${PACT_BROKER_URL}", port = "${PACT_BROKER_PORT}")
class VerifyPacts extends VerifyPactsTestTarget {

  @State(Array("test state"))
  def toDefaultState(): Unit = {
    System.out.println("Now service in test state")
  }

}

object VerifyPacts {
  private var application: ConfigurableApplicationContext = null

  @BeforeClass
  def startSpring(): Unit = {
    application = SpringApplication.run(classOf[Application])
  }

  @AfterClass
  def kill(): Unit = application.stop()

}