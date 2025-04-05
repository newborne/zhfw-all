package com.easyz.zhfw.controller.v1;

import com.easyz.zhfw.pojo.DateType;
import com.easyz.zhfw.pojo.RoomInfo;
import com.easyz.zhfw.pojo.StayPeriod;
import com.easyz.zhfw.service.HotelReservationWS;
import com.easyz.zhfw.vo.UnAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelReservationController {

    @Autowired
    private HotelReservationWS hotelReservationService;

    @GetMapping("/getAvailableRooms")
    public List<RoomInfo> getAvailableRooms(
            @RequestParam("checkinYear") int checkinYear,
            @RequestParam("checkinMonth") int checkinMonth,
            @RequestParam("checkinDate") int checkinDate,
            @RequestParam("checkoutYear") int checkoutYear,
            @RequestParam("checkoutMonth") int checkoutMonth,
            @RequestParam("checkoutDate") int checkoutDate) {
        // 创建并设置 checkin DateType
        DateType checkin = new DateType();
        checkin.setYear(checkinYear);
        checkin.setMonth(checkinMonth);
        checkin.setDate(checkinDate);

        // 创建并设置 checkout DateType
        DateType checkout = new DateType();
        checkout.setYear(checkoutYear);
        checkout.setMonth(checkoutMonth);
        checkout.setDate(checkoutDate);

        // 创建并设置 StayPeriod
        StayPeriod period = new StayPeriod();
        period.setCheckin(checkin);
        period.setCheckout(checkout);

        return hotelReservationService.getAvailableRooms(period);
    }

    @GetMapping("/bookRoom")
    public String bookRoom(
            @RequestParam("type") String type,
            @RequestParam("amount") int amount,
            @RequestParam("checkinYear") int checkinYear,
            @RequestParam("checkinMonth") int checkinMonth,
            @RequestParam("checkinDate") int checkinDate,
            @RequestParam("checkoutYear") int checkoutYear,
            @RequestParam("checkoutMonth") int checkoutMonth,
            @RequestParam("checkoutDate") int checkoutDate) throws UnAvailableException {
        // 创建并设置 checkin DateType
        DateType checkin = new DateType();
        checkin.setYear(checkinYear);
        checkin.setMonth(checkinMonth);
        checkin.setDate(checkinDate);

        // 创建并设置 checkout DateType
        DateType checkout = new DateType();
        checkout.setYear(checkoutYear);
        checkout.setMonth(checkoutMonth);
        checkout.setDate(checkoutDate);

        // 创建并设置 StayPeriod
        StayPeriod period = new StayPeriod();
        period.setCheckin(checkin);
        period.setCheckout(checkout);

        return hotelReservationService.bookRoom(type, amount, period);
    }

    @GetMapping("/cancelBooking")
    public void cancelBooking(@RequestParam("bookingID") String bookingID) {
        hotelReservationService.cancelBooking(bookingID);
    }
}
