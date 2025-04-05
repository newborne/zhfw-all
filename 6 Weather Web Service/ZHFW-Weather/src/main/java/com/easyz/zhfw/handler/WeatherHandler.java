package com.easyz.zhfw.handler;

import com.easyz.zhfw.pojo.Date;
import com.easyz.zhfw.pojo.WeatherInfo;
import com.easyz.zhfw.pojo.WeatherList;
import com.easyz.zhfw.pojo.WeatherType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class WeatherHandler extends DefaultHandler {
    private List<WeatherInfo> weatherInfos;
    private StringBuilder data;
    private WeatherInfo currentWeather;
    private Date currentDate;
    private WeatherList currentWeatherList;
    private boolean isDatePart;

    public WeatherHandler() {
        weatherInfos = new ArrayList<>();
        data = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data.setLength(0);
        if ("weather".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            currentWeather = new WeatherInfo();
            currentDate = new Date(); // 初始化 currentDate
        } else if ("date".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            isDatePart = true; // 表示这是日期的父元素
        } else if ("weathers".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            currentWeatherList = new WeatherList();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String trimmedData = data.toString().trim();
        if ("year".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentDate != null) {
                currentDate.setYear(Integer.parseInt(trimmedData));
                System.out.println("Parsed year: " + trimmedData);
            }
        } else if ("month".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentDate != null) {
                currentDate.setMonth(Integer.parseInt(trimmedData));
                System.out.println("Parsed month: " + trimmedData);
            }
        } else if ("date".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (isDatePart) {
                if (currentDate != null) {
                    currentDate.setDate(Integer.parseInt(trimmedData));
                    System.out.println("Parsed date: " + trimmedData);
                }
            } else {
                if (currentWeather != null && currentDate != null) {
                    System.out.println("Setting date: " + currentDate + " to weather: " + currentWeather);
                    currentWeather.setDate(currentDate);
                    currentDate = null; // 重置 currentDate
                }
            }
            isDatePart = false; // 重置 isDatePart 标志位
        } else if ("location".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentWeather != null) {
                currentWeather.setLocation(trimmedData);
                System.out.println("Parsed location: " + trimmedData);
            }
        } else if ("description".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentWeather != null) {
                currentWeather.setDescription(WeatherType.fromValue(trimmedData));
                System.out.println("Parsed description: " + trimmedData);
            }
        } else if ("temperature".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentWeather != null) {
                currentWeather.setTemperature(Double.parseDouble(trimmedData));
                System.out.println("Parsed temperature: " + trimmedData);
            }
        } else if ("weather".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentWeather != null) {
                weatherInfos.add(currentWeather);
                System.out.println("Added weather: " + currentWeather);
                currentWeather = null; // 重置 currentWeather
            }
        } else if ("weathers".equals(localName) && "http://www.example.org/WeatherDB".equals(uri)) {
            if (currentWeatherList != null) {
                for (WeatherInfo weather : weatherInfos) {
                    currentWeatherList.getWeather().add(weather);
                }
                weatherInfos.clear();
                System.out.println("Added all weathers to list");
            }
        }
    }

    public WeatherList getWeatherList() {
        return currentWeatherList;
    }
}
