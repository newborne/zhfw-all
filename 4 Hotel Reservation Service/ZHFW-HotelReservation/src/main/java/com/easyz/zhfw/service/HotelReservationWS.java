package com.easyz.zhfw.service;

import com.easyz.zhfw.pojo.RoomInfo;
import com.easyz.zhfw.pojo.StayPeriod;
import com.easyz.zhfw.vo.UnAvailableException;

import java.util.List;

public interface HotelReservationWS {

	/**
	 * Query the available rooms 
	 * @param period the period to stay at the hotel
	 * @return the list of available rooms
	 * 
	 * 
	 */
	public List<RoomInfo> getAvailableRooms(StayPeriod period);
	
	
	/**
	 * Book rooms  
	 * @param type the type of the room
	 * @param amount the amount of rooms to reserve
	 * @param period the period to stay at the hotel
	 * @return the ID of the booking (the ID is used to cancel the booking)
	 * @exception UnAvailableException
	 * 
	 * 
	 */
	public String bookRoom(String type, int amount, StayPeriod period) throws UnAvailableException;
	
	/**
	 * Cancel a booking
	 * @param bookingID the ID of a booking
	 * 
	 * 
	 */
	public void cancelBooking(String bookingID);
	
}
