package com.thoughtworks.petstore.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import com.github.fakemongo.Fongo
import com.mongodb.Mongo

abstract class AbstractFongoBaseConfiguration extends AbstractMongoConfiguration {

  @Autowired
  private var env: Environment = _

  override protected def getDatabaseName: String = env.getRequiredProperty("spring.data.mongodb.database")

  @throws[Exception]
  override def mongo: Mongo = new Fongo(getDatabaseName).getMongo
}
