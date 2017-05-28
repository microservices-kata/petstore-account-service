package com.thoughtworks.petstore.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
  * 启用Swagger2
  */
@Configuration
@EnableSwagger2 class Swagger2Config {
  @Bean def createRestApi: Docket = new Docket(DocumentationType.SWAGGER_2)
    .apiInfo(apiInfo)
    .groupName("")
    .pathMapping("")
    .select()
    .apis(RequestHandlerSelectors.basePackage("com.thoughtworks.petstore.user.controller"))
    .paths(PathSelectors.any())
    .build()

  private def apiInfo: ApiInfo = new ApiInfoBuilder()
    .title("PetStore User Service API")
    .description("").version("1.0")
    .termsOfServiceUrl("")
    .license("")
    .licenseUrl("")
    .build
}
