package com.easyz.zhfw.service;

import com.easyz.zhfw.pojo.Date;
import com.easyz.zhfw.pojo.WeatherInfo;

public interface WeatherWS {
	
	/**
	 *  Query the weather info
	 * 
	 * @param date the date of query
	 * @param location the location of the query
	 * @return the weather info
	 * 
	 *
	 */
	public WeatherInfo queryWeather(Date date, String location);

}
