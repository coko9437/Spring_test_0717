package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.WeatherDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WeatherServiceImpl.class)
@Log4j2
public class WeatherServiceTest {

    @Autowired
    private  WeatherService weatherService;

    @Test
    public void testGetWeather(){
        double lat = 37.57;
        double lon = 126.98;

        WeatherDTO dto = weatherService.getWeather(lat, lon);

        log.info("온도 : " + dto.getTemperature());
        log.info("상태 : " + dto.getInfo());
    }
}
