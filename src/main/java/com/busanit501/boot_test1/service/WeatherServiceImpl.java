package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.WeatherDTO;
import com.busanit501.boot_test1.mapper.WeatherCodeMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class WeatherServiceImpl implements WeatherService {


    @Override
    public WeatherDTO getWeather(double lat, double lon) {
        try {
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude="
                    + lat + "&longitude=" + lon + "&current_weather=true";

            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONObject json = new JSONObject(sb.toString());
            JSONObject weather = json.getJSONObject("current_weather");

            String temperature = String.valueOf(weather.getDouble("temperature"));
            int code = weather.getInt("weathercode");
            String info = WeatherCodeMapper.getInfo(code);

            return new WeatherDTO(temperature, info);

        } catch (Exception e) {
            log.error("날씨 정보 가져오기 실패", e);
            return new WeatherDTO("온도 오류", "상태 오류");
        }
    }
}
