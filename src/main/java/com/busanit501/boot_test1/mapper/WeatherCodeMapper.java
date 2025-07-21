package com.busanit501.boot_test1.mapper;

public class WeatherCodeMapper {
    public static String getInfo(int code) {
        return switch (code) { // weather코드로 날씨정보
            case 0 -> "맑음";
            case 1, 2, 3 -> "부분적으로 흐림";
            case 45, 48 -> "안개";
            case 51, 53, 55 -> "이슬비";
            case 61, 63, 65 -> "비";
            case 71, 73, 75 -> "눈";
            case 95 -> "번개";
            default -> "알 수 없음";
        };
    }
}

