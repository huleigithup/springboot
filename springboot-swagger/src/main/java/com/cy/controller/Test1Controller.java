package com.cy.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(produces = "application/json")
@RestController
public class Test1Controller {
    @ApiOperation("测试1-1接口")
    @RequestMapping(value = "/test1-1", method = RequestMethod.GET)
    public String test1() {
        return "test1-1";
    }

    @ApiOperation("测试1-2接口")
    @RequestMapping(value = "/test1-2", method = RequestMethod.GET)
    public String test2() {
        return "test1-2";
    }

}
