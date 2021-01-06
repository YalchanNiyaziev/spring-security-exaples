package com.yalco.springsecurity.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHome(){
        return "Home page";
    }
    @PostMapping
    public String post(){
        return "Home page post method hit";
    }
}
