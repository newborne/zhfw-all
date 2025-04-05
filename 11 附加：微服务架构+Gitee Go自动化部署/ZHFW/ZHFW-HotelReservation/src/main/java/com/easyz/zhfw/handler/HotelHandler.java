package com.easyz.zhfw.handler;

import com.easyz.zhfw.pojo.BookingInfo;
import com.easyz.zhfw.pojo.DateType;
import com.easyz.zhfw.pojo.RoomInfo;
import com.easyz.zhfw.pojo.StayPeriod;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelHandler extends DefaultHandler {
    private List<BookingInfo> bookings;
    private List<RoomInfo> rooms;
    private StringBuilder data;
    private BookingInfo currentBooking;
    private RoomInfo currentRoom;
    private StayPeriod currentStayPeriod;
    private String xmlFilePath;

    public HotelHandler(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        bookings = new ArrayList<>();
        rooms = new ArrayList<>();
        data = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data.setLength(0);
        if ("booking".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            currentBooking = new BookingInfo();
        } else if ("stayPeriod".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            currentStayPeriod = new StayPeriod();
        } else if ("room".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            currentRoom = new RoomInfo();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("bookingID".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            currentBooking.setBookingID(data.toString().trim());
        } else if ("type".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentBooking != null) {
                currentBooking.setType(data.toString().trim());
            } else if (currentRoom != null) {
                currentRoom.setType(data.toString().trim());
            }
        } else if ("amount".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentBooking != null) {
                currentBooking.setAmount(Integer.parseInt(data.toString().trim()));
            }
        } else if ("totalAmount".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentRoom != null) {
                currentRoom.setTotalAmount(Integer.parseInt(data.toString().trim()));
            }
        } else if ("rate".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentRoom != null) {
                currentRoom.setRate(Integer.parseInt(data.toString().trim()));
            }
        } else if ("year".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentStayPeriod != null) {
                if (currentStayPeriod.getCheckin() != null) {
                    currentStayPeriod.getCheckin().setYear(Integer.parseInt(data.toString().trim()));
                } else if (currentStayPeriod.getCheckout() != null) {
                    currentStayPeriod.getCheckout().setYear(Integer.parseInt(data.toString().trim()));
                }
            }
        } else if ("month".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentStayPeriod != null) {
                if (currentStayPeriod.getCheckin() != null) {
                    currentStayPeriod.getCheckin().setMonth(Integer.parseInt(data.toString().trim()));
                } else if (currentStayPeriod.getCheckout() != null) {
                    currentStayPeriod.getCheckout().setMonth(Integer.parseInt(data.toString().trim()));
                }
            }
        } else if ("date".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentStayPeriod != null) {
                if (currentStayPeriod.getCheckin() != null) {
                    currentStayPeriod.getCheckin().setDate(Integer.parseInt(data.toString().trim()));
                } else if (currentStayPeriod.getCheckout() != null) {
                    currentStayPeriod.getCheckout().setDate(Integer.parseInt(data.toString().trim()));
                }
            }
        } else if ("checkin".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentStayPeriod != null) {
                if (currentStayPeriod.getCheckin() == null) {
                    currentStayPeriod.setCheckin(new DateType());
                }
            }
        } else if ("checkout".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentStayPeriod != null) {
                if (currentStayPeriod.getCheckout() == null) {
                    currentStayPeriod.setCheckout(new DateType());
                }
            }
        } else if ("stayPeriod".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            if (currentBooking != null) {
                currentBooking.setStayPeriod(currentStayPeriod);
            }
            currentStayPeriod = null;
        } else if ("booking".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            bookings.add(currentBooking);
            currentBooking = null;
        } else if ("room".equals(localName) && "http://www.example.org/HotelDB".equals(uri)) {
            rooms.add(currentRoom);
            currentRoom = null;
        }
    }

    public List<BookingInfo> getBookings() {
        return bookings;
    }

    public List<RoomInfo> getRooms() {
        return rooms;
    }

    public void saveToXml() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            // 创建根元素
            Element rootElement = document.createElementNS("http://www.example.org/HotelDB", "hot:hotel");
            document.appendChild(rootElement);

            // 添加房间信息
            Element roomsElement = document.createElementNS("http://www.example.org/HotelDB", "hot:rooms");
            rootElement.appendChild(roomsElement);
            for (RoomInfo room : rooms) {
                Element roomElement = document.createElementNS("http://www.example.org/HotelDB", "hot:room");
                roomElement.appendChild(createElementWithText(document, "hot:type", room.getType()));
                roomElement.appendChild(createElementWithText(document, "hot:totalAmount", String.valueOf(room.getTotalAmount())));
                roomElement.appendChild(createElementWithText(document, "hot:rate", String.valueOf(room.getRate())));

                roomsElement.appendChild(roomElement);
            }

            // 添加预订信息
            Element bookingsElement = document.createElementNS("http://www.example.org/HotelDB", "hot:bookings");
            rootElement.appendChild(bookingsElement);
            for (BookingInfo booking : bookings) {
                Element bookingElement = document.createElementNS("http://www.example.org/HotelDB", "hot:booking");
                bookingElement.appendChild(createElementWithText(document, "hot:bookingID", booking.getBookingID()));
                bookingElement.appendChild(createElementWithText(document, "hot:type", booking.getType()));
                bookingElement.appendChild(createElementWithText(document, "hot:amount", String.valueOf(booking.getAmount())));

                Element stayPeriodElement = document.createElementNS("http://www.example.org/HotelDB", "hot:stayPeriod");
                stayPeriodElement.appendChild(createCheckinElement(document, booking.getStayPeriod().getCheckin()));
                stayPeriodElement.appendChild(createCheckoutElement(document, booking.getStayPeriod().getCheckout()));

                bookingElement.appendChild(stayPeriodElement);

                bookingsElement.appendChild(bookingElement);
            }



            // 写入文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(new File(xmlFilePath)));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element createElementWithText(Document document, String tagName, String text) {
        Element element = document.createElementNS("http://www.example.org/HotelDB", tagName);
        element.setTextContent(text);
        return element;
    }

    private Element createCheckinElement(Document document, DateType checkin) {
        Element checkinElement = document.createElementNS("http://www.example.org/HotelDB", "hot:checkin");
        checkinElement.appendChild(createElementWithText(document, "hot:year", String.valueOf(checkin.getYear()).trim()));
        checkinElement.appendChild(createElementWithText(document, "hot:month", String.valueOf(checkin.getMonth()).trim()));
        checkinElement.appendChild(createElementWithText(document, "hot:date", String.valueOf(checkin.getDate()).trim()));
        return checkinElement;
    }

    private Element createCheckoutElement(Document document, DateType checkout) {
        Element checkoutElement = document.createElementNS("http://www.example.org/HotelDB", "hot:checkout");
        checkoutElement.appendChild(createElementWithText(document, "hot:year", String.valueOf(checkout.getYear()).trim()));
        checkoutElement.appendChild(createElementWithText(document, "hot:month", String.valueOf(checkout.getMonth()).trim()));
        checkoutElement.appendChild(createElementWithText(document, "hot:date", String.valueOf(checkout.getDate()).trim()));
        return checkoutElement;
    }
}