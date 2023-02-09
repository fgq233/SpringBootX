package com.security.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello SpringSecurity";
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return "Index Page";
    }

    @RequestMapping("/user")
    @ResponseBody
    public String user() {
        return "User Page";
    }


    @RequestMapping("/organ")
    @ResponseBody
    @Secured({"ROLE_admin", "organAdd"})
    public String organ() {
        return "Organ Page";
    }

}
