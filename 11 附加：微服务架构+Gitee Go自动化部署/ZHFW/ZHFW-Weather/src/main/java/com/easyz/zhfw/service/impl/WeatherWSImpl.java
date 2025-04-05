package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.handler.WeatherHandler;
import com.easyz.zhfw.pojo.Date;
import com.easyz.zhfw.pojo.WeatherInfo;
import com.easyz.zhfw.service.WeatherWS;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherWSImpl implements WeatherWS {

    private final String xmlFilePath = "datasource/ds_2_6.xml"; // XML 文件路径

    private List<WeatherInfo> weatherList;
    private WeatherHandler weatherHandler;

    // 加载天气信息
    private void loadWeatherInfo() {
        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                throw new IOException("File not found: " + xmlFilePath);
            }
            XMLReader reader = XMLReaderFactory.createXMLReader();
            weatherHandler = new WeatherHandler();
            reader.setContentHandler(weatherHandler);
            reader.parse(new InputSource(file.toURI().toString()));
            weatherList = weatherHandler.getWeatherList().getWeather();
            System.out.println("Loaded " + weatherList.size() + " weather records.");
            for (WeatherInfo weather : weatherList) {
                System.out.println("Loaded weather: " + weather);
            }
        } catch (IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
            weatherList = Collections.emptyList(); // 确保 weatherList 不为 null
        }
    }

    /**
     * 查询天气信息
     *
     * @param date     查询的日期
     * @param location 查询的地点
     * @return 天气信息
     */
    @Override
    public WeatherInfo queryWeather(Date date, String location) {
        if (weatherList == null) {
            loadWeatherInfo();
        }
        System.out.println("Querying for date: " + date + ", location: " + location);
        for (WeatherInfo weather : weatherList) {
            System.out.println("Checking weather: " + weather);
            boolean dateMatches = weather.getDate().getYear() == date.getYear() &&
                                 weather.getDate().getMonth() == date.getMonth() &&
                                 weather.getDate().getDate() == date.getDate();
            boolean locationMatches = Objects.equals(weather.getLocation(), location);
            System.out.println("Date matches: " + dateMatches + ", Location matches: " + locationMatches);
            if (dateMatches && locationMatches) {
                System.out.println("Matched weather: " + weather);
                return weather;
            }
        }
        System.out.println("No matching weather found.");
        return null;
    }
}
