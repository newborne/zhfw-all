package com.easyz.zhfw.client;


import com.easyz.zhfw.pojo.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class HotelReservationClient {

    private static final String BASE_URL = "http://localhost:8081/hotel";

    @Autowired
    private RestTemplate restTemplate;

    public List<RoomInfo> getAvailableRooms(int checkinYear, int checkinMonth, int checkinDate,
                                            int checkoutYear, int checkoutMonth, int checkoutDate) {
        ResponseEntity<RoomInfo[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAvailableRooms?checkinYear={checkinYear}&checkinMonth={checkinMonth}&checkinDate={checkinDate}" +
                        "&checkoutYear={checkoutYear}&checkoutMonth={checkoutMonth}&checkoutDate={checkoutDate}",
                RoomInfo[].class,
                checkinYear, checkinMonth, checkinDate, checkoutYear, checkoutMonth, checkoutDate
        );
        return Arrays.asList(response.getBody());
    }

    public String bookRoom(String type, int amount, int checkinYear, int checkinMonth, int checkinDate,
                           int checkoutYear, int checkoutMonth, int checkoutDate) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                BASE_URL + "/bookRoom?type={type}&amount={amount}&checkinYear={checkinYear}&checkinMonth={checkinMonth}&checkinDate={checkinDate}" +
                        "&checkoutYear={checkoutYear}&checkoutMonth={checkoutMonth}&checkoutDate={checkoutDate}",
                String.class,
                type, amount, checkinYear, checkinMonth, checkinDate, checkoutYear, checkoutMonth, checkoutDate
        );
        return response.getBody();
    }

    public void cancelBooking(String bookingID) {
        restTemplate.getForEntity(
                BASE_URL + "/cancelBooking?bookingID={bookingID}",
                Void.class,
                bookingID
        );
    }
}
