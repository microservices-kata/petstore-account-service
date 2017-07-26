package com.thoughtworks.petstore.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@EnableAutoConfiguration(exclude = Array(classOf[EmbeddedMongoAutoConfiguration],
  classOf[MongoAutoConfiguration],
  classOf[MongoDataAutoConfiguration]))
@Configuration
@ComponentScan(basePackages = Array("com.isolisduran.springboot"),
  excludeFilters = Array(new ComponentScan.Filter(classes = Array(classOf[SpringBootApplication]))))
class ConfigServerWithFongoConfiguration extends AbstractFongoBaseConfiguration {}
