package com.busanit501.boot_test1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/weather_test")
    public String showWeatherTestPage() {
    return "weather_test"; // templates/weather_test.html
}
}
