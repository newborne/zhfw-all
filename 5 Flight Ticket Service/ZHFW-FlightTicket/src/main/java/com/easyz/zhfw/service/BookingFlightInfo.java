package com.easyz.zhfw.service;

import com.easyz.zhfw.pojo.AirlineInfo;
import com.easyz.zhfw.pojo.DateInfo;
import com.easyz.zhfw.vo.FlightUnAvailableException;

import java.util.List;

public interface BookingFlightInfo {
    
	/**
	 * Get all the available flight info
	 * @param departure departure city
	 * @param destination destination city
	 * @param date departure date
	 * @return available flight info
	 * 
	 * 
	 */
	public List<AirlineInfo> getFlightInfo(String departure, String destination, DateInfo date);
	
	/**
	 * Book a flight
	 * @param flightNumber the number of the flight
	 * @param date the departure date
	 * @param seats the number of seats to book
	 * @return the booking ID
	 * @exception FlightUnAvailableException
	 * 
	 *  
	 */
	public String bookFlight(String flightNumber, DateInfo date, int seats) throws FlightUnAvailableException;
	
	/**
	 * Cancel a booking
	 * @param bookingID the ID of the booking
	 * 
	 * 
	 */
	public void cancelBooking(String bookingID);
}
