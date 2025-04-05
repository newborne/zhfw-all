package com.easyz.zhfw.handler;

import com.easyz.zhfw.pojo.DateInfo;
import com.easyz.zhfw.pojo.BookingInfo;
import com.easyz.zhfw.pojo.AirlineInfo;
import com.easyz.zhfw.pojo.DateList;
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

public class FlightHandler extends DefaultHandler {
    private List<AirlineInfo> flights;
    private List<BookingInfo> bookings;
    private StringBuilder data;
    private AirlineInfo currentFlight;
    private BookingInfo currentBooking;
    private DateList currentDateList;
    private String xmlFilePath;

    public FlightHandler(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        flights = new ArrayList<>();
        bookings = new ArrayList<>();
        data = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data.setLength(0);
        if ("airline".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentFlight = new AirlineInfo();
        } else if ("booking".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentBooking = new BookingInfo();
        } else if ("dates".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentDateList = new DateList();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("flightNo".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            if (currentFlight != null) {
                currentFlight.setFlightNo(data.toString());
            } else if (currentBooking != null) {
                currentBooking.setFlightNo(data.toString());
            }
        } else if ("departure".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentFlight.setDeparture(data.toString());
        } else if ("destination".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentFlight.setDestination(data.toString());
        } else if ("seats".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            if (currentFlight != null) {
                currentFlight.setSeats(Integer.parseInt(data.toString()));
            } else if (currentBooking != null) {
                currentBooking.setSeats(Integer.parseInt(data.toString()));
            }
        } else if ("price".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentFlight.setPrice(Integer.parseInt(data.toString()));
        } else if ("date".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            if (currentDateList != null) {
                // 将日期字符串转换为 DateInfo 枚举对象
                DateInfo date = DateInfo.fromValue(data.toString());
                currentDateList.getDate().add(date);
            } else if (currentBooking != null) {
                // 直接设置日期
                DateInfo date = DateInfo.fromValue(data.toString());
                currentBooking.setDate(date);
            }
        } else if ("dates".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            if (currentFlight != null) {
                currentFlight.setDates(currentDateList);
            }
            currentDateList = null;
        } else if ("bookingID".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            currentBooking.setBookingID(data.toString());
        } else if ("airline".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            flights.add(currentFlight);
            currentFlight = null;
            System.out.println("Added flight: " + data.toString()); // 打印日志
        } else if ("booking".equals(localName) && "http://www.example.org/FlightDB".equals(uri)) {
            bookings.add(currentBooking);
            currentBooking = null;
        }
    }

    public List<AirlineInfo> getAirlines() {
        System.out.println("Total flights: " + flights.size()); // 打印日志
        for (AirlineInfo flight : flights) {
            System.out.println("Flight: " + flight.getFlightNo() + ", Departure: " + flight.getDeparture() + ", Destination: " + flight.getDestination());
        }
        return flights;
    }

    public List<BookingInfo> getBookings() {
        return bookings;
    }

    public void saveToXml() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            // 创建根元素
            Element rootElement = document.createElementNS("http://www.example.org/FlightDB", "flig:tickets");
            document.appendChild(rootElement);

            // 添加航班信息
            Element airlinesElement = document.createElementNS("http://www.example.org/FlightDB", "flig:airlines");
            rootElement.appendChild(airlinesElement);
            for (AirlineInfo flight : flights) {
                Element flightElement = document.createElementNS("http://www.example.org/FlightDB", "flig:airline");
                flightElement.appendChild(createElementWithText(document, "flig:flightNo", flight.getFlightNo()));
                flightElement.appendChild(createElementWithText(document, "flig:departure", flight.getDeparture()));
                flightElement.appendChild(createElementWithText(document, "flig:destination", flight.getDestination()));
                flightElement.appendChild(createElementWithText(document, "flig:seats", String.valueOf(flight.getSeats())));
                flightElement.appendChild(createElementWithText(document, "flig:price", String.valueOf(flight.getPrice())));

                Element datesElement = document.createElementNS("http://www.example.org/FlightDB", "flig:dates");
                for (DateInfo date : flight.getDates().getDate()) {
                    Element dateElement = document.createElementNS("http://www.example.org/FlightDB", "flig:date");
                    dateElement.setTextContent(date.value());
                    datesElement.appendChild(dateElement);
                }
                flightElement.appendChild(datesElement);

                airlinesElement.appendChild(flightElement);
            }

            // 添加预订信息
            Element bookingsElement = document.createElementNS("http://www.example.org/FlightDB", "flig:bookings");
            rootElement.appendChild(bookingsElement);
            for (BookingInfo booking : bookings) {
                Element bookingElement = document.createElementNS("http://www.example.org/FlightDB", "flig:booking");
                bookingElement.appendChild(createElementWithText(document, "flig:bookingID", booking.getBookingID()));
                bookingElement.appendChild(createElementWithText(document, "flig:flightNo", booking.getFlightNo()));
                bookingElement.appendChild(createElementWithText(document, "flig:seats", String.valueOf(booking.getSeats())));
                bookingElement.appendChild(createElementWithText(document, "flig:date", booking.getDate().value()));

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
        Element element = document.createElementNS("http://www.example.org/FlightDB", tagName);
        element.setTextContent(text);
        return element;
    }
}
