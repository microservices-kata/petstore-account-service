package com.thoughtworks.petstore.user.controller

import io.swagger.annotations.{ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, ResponseBody}
import com.google.common.collect.ImmutableMap

@Controller
@RequestMapping(value = Array("/app"))
class AppController {

  @Value("${app.lang}")
  var lang: String = _

  @ApiOperation(value = "Show programing language")
  @RequestMapping(value = Array("/lang"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getLanguage = ImmutableMap.of("language", lang)

}
