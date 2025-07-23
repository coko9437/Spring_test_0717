package com.busanit501.boot_test1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController2 {

    @GetMapping("/test")
    public String testHtml() {
        return "test";
    }
}
