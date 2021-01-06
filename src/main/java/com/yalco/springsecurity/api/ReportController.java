package com.yalco.springsecurity.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @GetMapping
    public String getReports(){
        return "reports";
    }
    @PostMapping
    public String addReport(){
        return "Report was successfully added";
    }
}
