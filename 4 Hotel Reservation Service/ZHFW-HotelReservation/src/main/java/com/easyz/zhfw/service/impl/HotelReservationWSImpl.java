package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.handler.HotelHandler;
import com.easyz.zhfw.pojo.BookingInfo;
import com.easyz.zhfw.pojo.DateType;
import com.easyz.zhfw.pojo.RoomInfo;
import com.easyz.zhfw.pojo.StayPeriod;
import com.easyz.zhfw.service.HotelReservationWS;
import com.easyz.zhfw.vo.UnAvailableException;
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
public class HotelReservationWSImpl implements HotelReservationWS {

    private final String xmlFilePath = "datasource/ds_2_4.xml"; // XML 文件路径

    private List<RoomInfo> roomList;
    private List<BookingInfo> bookingList;
    private HotelHandler hotelHandler;

    // 加载酒店房间和预订信息
    private void loadRoomsAndBookings() {
        try {
            // 创建SAX解析器
            XMLReader reader = XMLReaderFactory.createXMLReader();
            hotelHandler = new HotelHandler(xmlFilePath);
            reader.setContentHandler(hotelHandler);
            // 解析XML文件
            reader.parse(new InputSource(new File(xmlFilePath).toURI().toString().trim()));
            roomList = hotelHandler.getRooms();
            bookingList = hotelHandler.getBookings();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            roomList = Collections.emptyList();
            bookingList = Collections.emptyList();
        }
    }

    /**
     * 获取所有可用的房间信息
     *
     * @param period 入住期间
     * @return 可用的房间信息
     */
    @Override
    public List<RoomInfo> getAvailableRooms(StayPeriod period) {
        if (roomList == null) {
            loadRoomsAndBookings();
        }
        List<RoomInfo> filteredRooms = roomList.stream()
                .map(room -> {
                    int availableAmount = calculateAvailableAmount(room, period);
                    if (availableAmount > 0) {
                        RoomInfo availableRoom = new RoomInfo();
                        availableRoom.setType(room.getType());
                        availableRoom.setTotalAmount(availableAmount);
                        availableRoom.setRate(room.getRate());
                        return availableRoom;
                    }
                    return null;
                })
                .filter(room -> room != null)
                .collect(Collectors.toList());

        // 打印日志，确认过滤后的房间列表
        System.out.println("Filtered Rooms: " + filteredRooms.size());
        for (RoomInfo room : filteredRooms) {
            System.out.println("Room: " + room.getType() + ", Total Rooms: " + room.getTotalAmount() + " Rate: " + room.getRate());
        }

        return filteredRooms;
    }

private int calculateAvailableAmount(RoomInfo room, StayPeriod period) {
    int totalAmount = room.getTotalAmount();
    int bookedAmount = 0;

    for (BookingInfo booking : bookingList) {
        if (booking.getType().equals(room.getType())) {
            StayPeriod bookingPeriod = booking.getStayPeriod();
            if (isOverlapping(period, bookingPeriod)) {
                bookedAmount += booking.getAmount();
            }
        }
    }

    // 计算指定日期的可用房间数量
    int availableAmount = totalAmount - bookedAmount;

    System.out.println("Calculate Available Amount: Room Type: " + room.getType() + ", Total Amount: " + totalAmount + ", Booked Amount: " + bookedAmount + ", Available Amount: " + availableAmount);
    return Math.max(availableAmount, 0);
}





/**
 * 判断两个入住期间是否有重叠
 *
 * @param period1 第一个入住期间
 * @param period2 第二个入住期间
 * @return 是否有重叠
 */
private boolean isOverlapping(StayPeriod period1, StayPeriod period2) {
    DateType checkin1 = period1.getCheckin();
    DateType checkout1 = period1.getCheckout();
    DateType checkin2 = period2.getCheckin();
    DateType checkout2 = period2.getCheckout();

    // 检查是否有重叠
    return !(compareDates(checkout1, checkin2) || compareDates(checkout2, checkin1));
}

    /**
     * 比较两个 DateType 对象的日期，判断第一个是否在第二个之前
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 如果 date1 在 date2 之前返回 true，否则返回 false
     */
    private boolean compareDates(DateType date1, DateType date2) {
        if (date1.getYear() < date2.getYear()) {
            return true;
        } else if (date1.getYear() > date2.getYear()) {
            return false;
        } else {
            if (date1.getMonth() < date2.getMonth()) {
                return true;
            } else if (date1.getMonth() > date2.getMonth()) {
                return false;
            } else {
                return date1.getDate() < date2.getDate();
            }
        }
    }

@Override
public String bookRoom(String type, int amount, StayPeriod period) throws UnAvailableException {
    if (roomList == null) {
        loadRoomsAndBookings();
    }
    Optional<RoomInfo> optionalRoom = roomList.stream()
            .filter(room -> type.equals(room.getType()) && calculateAvailableAmount(room, period) >= amount)
            .findFirst();
    if (!optionalRoom.isPresent()) {
        throw new UnAvailableException("房间不可用或数量不足");
    }
    RoomInfo room = optionalRoom.get();
    System.out.println("Before booking: Room Type: " + room.getType() + ", Total Amount: " + room.getTotalAmount());

    // 生成预订ID（这里简单生成一个随机字符串）
    String bookingID = "H" + System.currentTimeMillis();
    BookingInfo booking = new BookingInfo();
    booking.setBookingID(bookingID);
    booking.setType(type);
    booking.setAmount(amount);
    booking.setStayPeriod(period);
    bookingList.add(booking);

    // 保存到XML文件
    hotelHandler.saveToXml();

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
            loadRoomsAndBookings();
        }
        Optional<BookingInfo> optionalBooking = bookingList.stream()
                .filter(booking -> bookingID.equals(booking.getBookingID()))
                .findFirst();
        if (optionalBooking.isPresent()) {
            BookingInfo booking = optionalBooking.get();
            Optional<RoomInfo> optionalRoom = roomList.stream()
                    .filter(room -> room.getType().equals(booking.getType()))
                    .findFirst();
            if (optionalRoom.isPresent()) {
                RoomInfo room = optionalRoom.get();
                System.out.println("Before cancel: Room Type: " + room.getType() + ", Total Amount: " + room.getTotalAmount());
                room.setTotalAmount(room.getTotalAmount() + booking.getAmount());
                System.out.println("After cancel: Room Type: " + room.getType() + ", Total Amount: " + room.getTotalAmount());
                bookingList.remove(booking);

                // 保存到XML文件
                hotelHandler.saveToXml();
            }
        }
    }
}
