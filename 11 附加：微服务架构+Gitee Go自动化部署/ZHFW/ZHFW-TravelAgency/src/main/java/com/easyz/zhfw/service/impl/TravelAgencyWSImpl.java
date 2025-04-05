package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.client.*;
import com.easyz.zhfw.pojo.*;
import com.easyz.zhfw.service.TravelAgencyWS;
import com.easyz.zhfw.vo.TravelAgencyRequestType;
import com.easyz.zhfw.vo.TravelAgencyResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Service
public class TravelAgencyWSImpl implements TravelAgencyWS {

    private static final Logger logger = LoggerFactory.getLogger(TravelAgencyWSImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FlightTicketClient flightTicketClient;

    @Autowired
    private HotelReservationClient hotelReservationClient;

    @Autowired
    private BookStoreClient bookStoreClient;

    @Autowired
    private WarehouseClient warehouseClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private ServiceRetrievalClient serviceRetrievalClient;

    private boolean isServiceAvailable(String serviceName) {
        List<ServiceInfo> services = serviceRetrievalClient.search(serviceName);
        return services != null && !services.isEmpty();
    }

    @Override
    public TravelAgencyResponseType travelAgencyRequest(TravelAgencyRequestType request) {
        TravelAgencyResponseType response = new TravelAgencyResponseType();
        response.setSuccessful(false); // 初始化响应对象，设置成功标志为 false

        String flightBookingId = null;
        String hotelBookingId = null;
        String holdingID = null;

        try {
            // Step 1: Receive user travel information
            String name = request.getName(); // 获取用户姓名
            String address = request.getAddress(); // 获取用户地址
            String departureCity = request.getDepartureCity(); // 获取出发城市
            String destinationCity = request.getDestinationCity(); // 获取目的城市
            Date departureDate = request.getDepartureDate(); // 获取出发日期
            Date returnDate = request.getReturnDate(); // 获取返回日期

            logger.info("Received user travel information: name={}, address={}, departureCity={}, destinationCity={}, departureDate={}, returnDate={}",
                    name, address, departureCity, destinationCity, departureDate, returnDate); // 记录接收到的用户旅行信息

            // Check if required services are available
            if (!isServiceAvailable("zhfw-flight-ticket-service") ||
                !isServiceAvailable("zhfw-hotel-reservation-service") ||
                !isServiceAvailable("zhfw-book-store-service") ||
                !isServiceAvailable("zhfw-warehouse-service") ||
                !isServiceAvailable("zhfw-payment-service") ||
                !isServiceAvailable("zhfw-weather-service")) {
                throw new RuntimeException("One or more required services are not available");
            }

            // Convert dates to string format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String departureDateString = dateFormat.format(departureDate); // 将出发日期转换为字符串格式
            String returnDateString = dateFormat.format(returnDate); // 将返回日期转换为字符串格式

            // Convert departureDateString to DateInfo
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
            String dayOfWeek = dayFormat.format(departureDate); // 获取出发日期的星期几
            DateInfo departureDateInfo = DateInfo.fromValue(dayOfWeek); // 将星期几转换为 DateInfo 对象

            logger.info("Converted departure date to DateInfo: {}", departureDateInfo); // 记录转换后的出发日期信息

            // Step 2: Query available flights
            List<AirlineInfo> availableFlights = flightTicketClient.getFlightInfo(departureCity, destinationCity, departureDateInfo); // 查询可用航班
            if (availableFlights == null || availableFlights.isEmpty()) {
                throw new RuntimeException("No available flights found"); // 如果没有找到可用航班，抛出异常
            }

            logger.info("Available flights found: {}", availableFlights); // 记录找到的可用航班

            // For simplicity, we assume the first available flight is booked
            AirlineInfo selectedFlight = availableFlights.get(0); // 假设第一个可用航班被预订
            String flightNumber = selectedFlight.getFlightNo(); // 获取航班编号

            // Step 3: Book flight
            flightBookingId = flightTicketClient.bookFlight(flightNumber, departureDateInfo, 1); // 预订航班
            if (flightBookingId == null || flightBookingId.isEmpty()) {
                throw new RuntimeException("Flight booking failed"); // 如果预订失败，抛出异常
            }

            logger.info("Flight booked successfully: flightNumber={}, flightBookingId={}", flightNumber, flightBookingId); // 记录预订成功的航班信息

            // Step 4: Book hotel
            StayPeriod stayPeriod = new StayPeriod(); // 创建住宿期间对象

            // Create DateType objects for check-in and check-out dates
            DateType checkinDateType = parseDateType(departureDateString); // 将出发日期字符串转换为 DateType 对象
            DateType checkoutDateType = parseDateType(returnDateString); // 将返回日期字符串转换为 DateType 对象

            stayPeriod.setCheckin(checkinDateType); // 设置入住日期
            stayPeriod.setCheckout(checkoutDateType); // 设置退房日期

            List<RoomInfo> availableRooms = hotelReservationClient.getAvailableRooms(
                    checkinDateType.getYear(),
                    checkinDateType.getMonth(),
                    checkinDateType.getDate(),
                    checkoutDateType.getYear(),
                    checkoutDateType.getMonth(),
                    checkoutDateType.getDate()
            ); // 查询可用房间

            if (availableRooms == null || availableRooms.isEmpty()) {
                flightTicketClient.cancelBooking(flightBookingId); // 如果没有找到可用房间，取消航班预订
                throw new RuntimeException("No available rooms found"); // 抛出异常
            }

            logger.info("Available rooms found: {}", availableRooms); // 记录找到的可用房间

            // For simplicity, we assume the first available room is booked
            RoomInfo selectedRoom = availableRooms.get(0); // 假设第一个可用房间被预订
            String roomType = selectedRoom.getType(); // 获取房间类型

            hotelBookingId = hotelReservationClient.bookRoom(roomType, 1,
                    checkinDateType.getYear(),
                    checkinDateType.getMonth(),
                    checkinDateType.getDate(),
                    checkoutDateType.getYear(),
                    checkoutDateType.getMonth(),
                    checkoutDateType.getDate()
            ); // 预订房间

            if (hotelBookingId == null || hotelBookingId.isEmpty()) {
                flightTicketClient.cancelBooking(flightBookingId); // 如果预订失败，取消航班预订
                throw new RuntimeException("Hotel booking failed"); // 抛出异常
            }

            logger.info("Hotel booked successfully: roomType={}, hotelBookingId={}", roomType, hotelBookingId); // 记录预订成功的酒店信息

            // Step 5: Get travel guide from warehouse
            String bookName = destinationCity.toLowerCase() + "-guideline"; // 构建旅行指南书名
            List<BookInfo> bookInfos = bookStoreClient.getBooksByTitle(bookName); // 查询旅行指南
            if (bookInfos == null || bookInfos.isEmpty()) {
                throw new RuntimeException("No travel guide found for the destination city"); // 如果没有找到旅行指南，抛出异常
            }

            logger.info("Travel guides found: {}", bookInfos); // 记录找到的旅行指南

            BookInfo selectedBook = bookInfos.get(0); // 选择第一个旅行指南
            String resourceId = selectedBook.getResourceID(); // 获取资源 ID
            int quantity = warehouseClient.query(resourceId); // 查询仓库中的库存数量

            if (quantity > 0) {
                // Deduct 1 from the warehouse
                warehouseClient.pickupItems(resourceId, 1);
                logger.info("Deducted 1 item from warehouse: resourceId={}, remaining quantity={}", resourceId, warehouseClient.query(resourceId));
                response.setSuccessful(true);
                response.setMessage("Successful, no need to complement warehouse.");
            } else {
                // Complement warehouse with 10 books
                double price = selectedBook.getPrice(); // 获取书籍价格
                int totalCost = (int) (price * 10); // 计算补充库存的总成本
                boolean transferSuccess = paymentClient.transfer("11111", "22222", totalCost); // 转账支付
                if (!transferSuccess) {
                    throw new RuntimeException("Payment transfer failed"); // 如果转账失败，抛出异常
                }
                warehouseClient.complementStock(resourceId, 10); // 补充库存
                quantity = warehouseClient.query(resourceId); // 重新查询库存数量
                logger.info("Warehouse stock updated: resourceId={}, quantity={}", resourceId, quantity); // 记录更新后的库存信息

                // Deduct 1 from the warehouse
                warehouseClient.pickupItems(resourceId, 1);
                logger.info("Deducted 1 item from warehouse: resourceId={}, remaining quantity={}", resourceId, warehouseClient.query(resourceId));
                response.setSuccessful(true);
                response.setMessage("Successful, need to complement warehouse.");
            }

//            // Step 6: Hold items in the warehouse
//            holdingID = warehouseClient.holdItems(resourceId, 1); // 保留物品
//            if (holdingID == null || holdingID.isEmpty()) {
//                throw new RuntimeException("Failed to hold items in the warehouse"); // 如果保留失败，抛出异常
//            }

            logger.info("Items held in the warehouse: holdingID={}", holdingID); // 记录保留的物品信息

            // Step 7: Provide receipt information
            Map<String, String> itineraries = new HashMap<>();
            itineraries.put("Flight", flightBookingId);
            itineraries.put("Hotel", hotelBookingId);
            response.setItineraries(itineraries); // 设置行程信息

            logger.info("Response prepared: successful={}, itineraries={}",
                    response.isSuccessful(), response.getItineraries()); // 记录准备好的响应信息

        } catch (Exception e) {
            response.setSuccessful(false); // 设置成功标志为 false
            response.setMessage("Not successful. The flight booking, hotel booking, account info, and warehouse info should be the same as before the application is executed."); // 设置错误信息

            // Cancel flight booking if it was made
            if (flightBookingId != null && !flightBookingId.isEmpty()) {
                flightTicketClient.cancelBooking(flightBookingId);
                logger.info("Flight booking canceled: flightBookingId={}", flightBookingId);
            }

            // Cancel hotel booking if it was made
            if (hotelBookingId != null && !hotelBookingId.isEmpty()) {
                hotelReservationClient.cancelBooking(hotelBookingId);
                logger.info("Hotel booking canceled: hotelBookingId={}", hotelBookingId);
            }

//            // Return held items to the warehouse if they were held
//            if (holdingID != null && !holdingID.isEmpty()) {
//                warehouseClient.cancelHoldingItems(holdingID);
//                logger.info("Held items returned to the warehouse: holdingID={}", holdingID);
//            }

            logger.error("Error processing travel agency request: {}", e.getMessage(), e); // 记录错误信息
        }

        return response; // 返回响应对象
    }

    private DateType parseDateType(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString); // 解析日期字符串
        DateType dateType = new DateType(); // 创建 DateType 对象
        dateType.setYear(date.getYear() + 1900); // 设置年份（Date.getYear() 返回的是年份减去 1900）
        dateType.setMonth(date.getMonth() + 1); // 设置月份（Date.getMonth() 返回的是月份减去 1）
        dateType.setDate(date.getDate()); // 设置日期
        return dateType; // 返回 DateType 对象
    }
}
