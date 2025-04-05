
package com.easyz.zhfw.pojo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.easyz.zhfw.pojo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Hotel_QNAME = new QName("http://www.example.org/HotelDB", "hotel");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyz.zhfw.pojo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HotelInfo }
     * 
     */
    public HotelInfo createHotelInfo() {
        return new HotelInfo();
    }

    /**
     * Create an instance of {@link StayPeriod }
     * 
     */
    public StayPeriod createStayPeriodType() {
        return new StayPeriod();
    }

    /**
     * Create an instance of {@link RoomInfo }
     * 
     */
    public RoomInfo createRoomInfo() {
        return new RoomInfo();
    }

    /**
     * Create an instance of {@link RoomList }
     * 
     */
    public RoomList createRoomList() {
        return new RoomList();
    }

    /**
     * Create an instance of {@link BookingList }
     * 
     */
    public BookingList createBookingList() {
        return new BookingList();
    }

    /**
     * Create an instance of {@link DateType }
     * 
     */
    public DateType createDateType() {
        return new DateType();
    }

    /**
     * Create an instance of {@link BookingInfo }
     * 
     */
    public BookingInfo createBookingInfo() {
        return new BookingInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/HotelDB", name = "hotel")
    public JAXBElement<HotelInfo> createHotel(HotelInfo value) {
        return new JAXBElement<HotelInfo>(_Hotel_QNAME, HotelInfo.class, null, value);
    }

}
