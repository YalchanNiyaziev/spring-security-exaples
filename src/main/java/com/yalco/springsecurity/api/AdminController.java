package com.yalco.springsecurity.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public String getAdmin() {
        return "Admin";
    }
    @PostMapping
    public String postAdmin(){
        return "Admin Post method hit";
    }
}
