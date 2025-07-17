package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.WeatherDTO;

public interface WeatherService {
    WeatherDTO getWeather(double lat, double lon);
}
