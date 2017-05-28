package com.thoughtworks.petstore.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class Application

object Application extends App {
  SpringApplication.run(classOf[Application], args: _*)
}
