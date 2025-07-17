package com.busanit501.boot_test1.service;

import com.busanit501.boot_test1.dto.WeatherDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
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
            String description = "구름 많음"; // 상태 설명은 없어서 하드코딩

            return new WeatherDTO(temperature, description);

        } catch (Exception e) {
            return new WeatherDTO("N/A", "불러오기 실패");
        }
    }
}
