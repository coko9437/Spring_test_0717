package com.busanit501.boot_test1.controller;

import com.busanit501.boot_test1.dto.WeatherDTO;
import com.busanit501.boot_test1.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public WeatherDTO getWeather(
            @RequestParam double lat,
            @RequestParam double lon) {

        return weatherService.getWeather(lat, lon);
    }

}
