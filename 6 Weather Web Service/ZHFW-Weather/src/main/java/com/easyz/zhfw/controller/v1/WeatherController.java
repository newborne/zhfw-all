package com.easyz.zhfw.controller.v1;

import com.easyz.zhfw.pojo.Date;
import com.easyz.zhfw.pojo.WeatherInfo;
import com.easyz.zhfw.service.WeatherWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WeatherController {

    @Autowired
    private WeatherWS weatherService;

    /**
     * 查询天气信息
     *
     * @param year      年份
     * @param month     月份
     * @param day       日期
     * @param location  地点
     * @return 天气信息
     */
    @GetMapping("/queryWeather")
    public WeatherInfo queryWeather(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam("date") int day,
            @RequestParam("location") String location) {

        // 创建并设置 Date 对象
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);

        return weatherService.queryWeather(date, location);
    }
}
