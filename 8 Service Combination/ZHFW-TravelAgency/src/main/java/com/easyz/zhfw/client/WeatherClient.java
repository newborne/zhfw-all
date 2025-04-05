package com.easyz.zhfw.client;

import com.easyz.zhfw.pojo.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    private static final String BASE_URL = "http://zhfw-weather:8087";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherInfo queryWeather(int year, int month, int day, String location) {
        ResponseEntity<WeatherInfo> response = restTemplate.getForEntity(
                BASE_URL + "/queryWeather?year={year}&month={month}&date={day}&location={location}",
                WeatherInfo.class,
                year, month, day, location
        );
        return response.getBody();
    }
}
