package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.handler.FlightHandler;
import com.easyz.zhfw.pojo.AirlineInfo;
import com.easyz.zhfw.pojo.BookingInfo;
import com.easyz.zhfw.pojo.DateInfo;
import com.easyz.zhfw.service.BookingFlightInfo;
import com.easyz.zhfw.vo.FlightUnAvailableException;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingFlightInfoImpl implements BookingFlightInfo {

    private final String xmlFilePath = "datasource/ds_2_5.xml"; // XML 文件路径

    private List<AirlineInfo> airlineList;
    private List<BookingInfo> bookingList;
    private FlightHandler flightHandler;

    // 加载航班列表和预订列表
    private void loadFlightsAndBookings() {
        try {
            // 创建SAX解析器
            XMLReader reader = XMLReaderFactory.createXMLReader();
            flightHandler = new FlightHandler(xmlFilePath);
            reader.setContentHandler(flightHandler);
            // 解析XML文件
            reader.parse(new InputSource(new File(xmlFilePath).toURI().toString()));
            airlineList = flightHandler.getAirlines();
            bookingList = flightHandler.getBookings();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            airlineList = Collections.emptyList();
            bookingList = Collections.emptyList();
        }
        System.out.println("Loaded Flights: " + airlineList.size());
        System.out.println("Loaded Bookings: " + bookingList.size());
    }

    /**
     * 获取所有可用的航班信息
     *
     * @param departure   出发城市
     * @param destination 目的地城市
     * @param date        出发日期
     * @return 可用的航班信息
     */
    @Override
public List<AirlineInfo> getFlightInfo(String departure, String destination, DateInfo date) {
    if (airlineList == null) {
        loadFlightsAndBookings();
    }
    System.out.println("Getting flight info for departure: " + departure + ", destination: " + destination + ", date: " + date);

    List<AirlineInfo> filteredFlights = airlineList.stream()
            .filter(flight -> departure.equals(flight.getDeparture())
                    && destination.equals(flight.getDestination())
                    && flight.getDates().getDate().contains(date))
            .filter(flight -> {
                int bookedSeats = bookingList.stream()
                        .filter(booking -> flight.getFlightNo().equals(booking.getFlightNo()) && date.equals(booking.getDate()))
                        .mapToInt(BookingInfo::getSeats)
                        .sum();
                int availableSeats = flight.getSeats() - bookedSeats;
                System.out.println("Flight: " + flight.getFlightNo() + ", Total Seats: " + flight.getSeats() + ", Booked Seats: " + bookedSeats + ", Available Seats: " + availableSeats);
                return availableSeats > 0;
            })
            .peek(flight -> {
                int bookedSeats = bookingList.stream()
                        .filter(booking -> flight.getFlightNo().equals(booking.getFlightNo()) && date.equals(booking.getDate()))
                        .mapToInt(BookingInfo::getSeats)
                        .sum();
                flight.setSeats(flight.getSeats() - bookedSeats); // 设置可用座位数
            })
            .collect(Collectors.toList());

    // 打印日志，确认过滤后的航班列表
    System.out.println("Filtered Flights: " + filteredFlights.size());
    for (AirlineInfo flight : filteredFlights) {
        System.out.println("Flight: " + flight.getFlightNo() + ", Departure: " + flight.getDeparture() + ", Destination: " + flight.getDestination() + ", Available Seats: " + flight.getSeats());
    }

    return filteredFlights;
}


    /**
     * 预订航班
     *
     * @param flightNumber 航班号
     * @param date         出发日期
     * @param seats        预订的座位数
     * @return 预订ID
     * @throws FlightUnAvailableException
     */
    @Override
    public String bookFlight(String flightNumber, DateInfo date, int seats) throws FlightUnAvailableException {
        if (airlineList == null) {
            loadFlightsAndBookings();
        }
        System.out.println("Booking flight: " + flightNumber + ", date: " + date + ", seats: " + seats);

        Optional<AirlineInfo> optionalFlight = airlineList.stream()
                .filter(flight -> flightNumber.equals(flight.getFlightNo()) && flight.getDates().getDate().contains(date))
                .findFirst();
        if (!optionalFlight.isPresent()) {
            throw new FlightUnAvailableException("航班不可用");
        }
        AirlineInfo flight = optionalFlight.get();

        int bookedSeats = bookingList.stream()
                .filter(booking -> flight.getFlightNo().equals(booking.getFlightNo()) && date.equals(booking.getDate()))
                .mapToInt(BookingInfo::getSeats)
                .sum();
        int availableSeats = flight.getSeats() - bookedSeats;
        System.out.println("Flight: " + flight.getFlightNo() + ", Total Seats: " + flight.getSeats() + ", Booked Seats: " + bookedSeats + ", Available Seats: " + availableSeats);

        if (availableSeats < seats) {
            throw new FlightUnAvailableException("座位不足");
        }

        // 生成预订ID（这里简单生成一个随机字符串）
        String bookingID = "B" + System.currentTimeMillis();
        BookingInfo booking = new BookingInfo();
        booking.setBookingID(bookingID);
        booking.setFlightNo(flightNumber);
        booking.setSeats(seats);
        booking.setDate(date);
        bookingList.add(booking);

        // 保存到XML文件
        flightHandler.saveToXml();

        System.out.println("Flight booked successfully: " + bookingID);
        return bookingID;
    }

    /**
     * 取消预订
     *
     * @param bookingID 预订ID
     */
    @Override
    public void cancelBooking(String bookingID) {
        if (bookingList == null) {
            loadFlightsAndBookings();
        }
        System.out.println("Canceling booking: " + bookingID);

        Optional<BookingInfo> optionalBooking = bookingList.stream()
                .filter(booking -> bookingID.equals(booking.getBookingID()))
                .findFirst();
        if (optionalBooking.isPresent()) {
            BookingInfo booking = optionalBooking.get();
            Optional<AirlineInfo> optionalFlight = airlineList.stream()
                    .filter(flight -> flight.getFlightNo().equals(booking.getFlightNo()) && flight.getDates().getDate().contains(booking.getDate()))
                    .findFirst();
            if (optionalFlight.isPresent()) {
                AirlineInfo flight = optionalFlight.get();
                int bookedSeats = bookingList.stream()
                        .filter(b -> b.getFlightNo().equals(flight.getFlightNo()) && b.getDate().equals(booking.getDate()))
                        .mapToInt(BookingInfo::getSeats)
                        .sum();
                int availableSeats = flight.getSeats() - bookedSeats;
                System.out.println("Before cancellation: Flight: " + flight.getFlightNo() + ", Total Seats: " + flight.getSeats() + ", Booked Seats: " + bookedSeats + ", Available Seats: " + availableSeats);

                // 更新已预订座位数
                bookedSeats -= booking.getSeats();
                availableSeats = flight.getSeats() - bookedSeats;
                System.out.println("After cancellation: Flight: " + flight.getFlightNo() + ", Total Seats: " + flight.getSeats() + ", Booked Seats: " + bookedSeats + ", Available Seats: " + availableSeats);

                bookingList.remove(booking);

                // 保存到XML文件
                flightHandler.saveToXml();
            }
        }
    }
}
