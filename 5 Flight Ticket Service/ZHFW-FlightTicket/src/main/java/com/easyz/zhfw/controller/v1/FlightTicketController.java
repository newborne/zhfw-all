package com.easyz.zhfw.controller.v1;

import com.easyz.zhfw.pojo.AirlineInfo;

import com.easyz.zhfw.pojo.DateInfo;
import com.easyz.zhfw.service.BookingFlightInfo;
import com.easyz.zhfw.vo.FlightUnAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class FlightTicketController {

    private final BookingFlightInfo bookingFlightInfo;

    @Autowired
    public FlightTicketController(BookingFlightInfo bookingFlightInfo) {
        this.bookingFlightInfo = bookingFlightInfo;
    }

    /**
     * 获取可用的航班信息
     *
     * @param departure   出发城市
     * @param destination 目的地城市
     * @param date        出发日期
     * @return 可用的航班信息列表
     */
    @GetMapping("getFlightInfo")
    public ResponseEntity<List<AirlineInfo>> getFlightInfo(@RequestParam String departure,
                                                           @RequestParam String destination,
                                                           @RequestParam DateInfo date) {

        List<AirlineInfo> availableFlights = bookingFlightInfo.getFlightInfo(departure, destination, date);
        return ResponseEntity.ok(availableFlights);
    }

    /**
     * 预订航班
     *
     * @param flightNumber 航班号
     * @param date         出发日期
     * @param seats        预订的座位数
     * @return 预订ID
     */
    @GetMapping("bookFlight")
    public ResponseEntity<String> bookFlight(@RequestParam String flightNumber,
                                             @RequestParam DateInfo date,
                                             @RequestParam int seats) {
        try {
            String bookingID = bookingFlightInfo.bookFlight(flightNumber, date, seats);
            return ResponseEntity.ok(bookingID);
        } catch (FlightUnAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 取消预订
     *
     * @param bookingID 预订ID
     */
    @GetMapping("cancelBooking")
    public ResponseEntity<Void> cancelBooking(@RequestParam String bookingID) {
        bookingFlightInfo.cancelBooking(bookingID);
        // 返回带有消息的响应
        return ResponseEntity.ok().body(null);
    }
}
