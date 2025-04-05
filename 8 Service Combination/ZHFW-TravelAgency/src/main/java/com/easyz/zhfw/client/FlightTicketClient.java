package com.easyz.zhfw.client;

import com.easyz.zhfw.pojo.AirlineInfo;
import com.easyz.zhfw.pojo.DateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FlightTicketClient {

    private static final String BASE_URL = "http://zhfw-flightticket:8086";

    @Autowired
    private RestTemplate restTemplate;

    public List<AirlineInfo> getFlightInfo(String departure, String destination, DateInfo date) {
        ResponseEntity<AirlineInfo[]> response = restTemplate.getForEntity(
                BASE_URL + "/getFlightInfo?departure={departure}&destination={destination}&date={date}",
                AirlineInfo[].class,
                departure, destination, date
        );
        return Arrays.asList(response.getBody());
    }

    public String bookFlight(String flightNumber, DateInfo date, int seats) {
        System.out.println(flightNumber+ " " + date + " " + seats);
        ResponseEntity<String> response = restTemplate.getForEntity(
                BASE_URL + "/bookFlight?flightNumber={flightNumber}&date={date}&seats={seats}",
                String.class,
                flightNumber, date, seats
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
