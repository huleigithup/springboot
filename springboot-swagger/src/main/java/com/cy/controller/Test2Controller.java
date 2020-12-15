package com.cy.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(produces = "application/json")
@RestController
public class Test2Controller {
    @ApiOperation("测试2-1接口")
    @RequestMapping(value = "/test2-1", method = RequestMethod.GET)
    public String test1() {
        return "test2-1";
    }

    @ApiOperation("测试2-1接口")
    @RequestMapping(value = "/test2-2", method = RequestMethod.GET)
    public String test2() {
        return "test2-2";
    }

}
