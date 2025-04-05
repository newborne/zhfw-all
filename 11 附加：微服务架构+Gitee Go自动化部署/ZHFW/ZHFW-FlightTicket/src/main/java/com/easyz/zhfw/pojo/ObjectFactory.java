
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

    private final static QName _Tickets_QNAME = new QName("http://www.example.org/FlightDB", "tickets");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyz.zhfw.pojo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TicketInfo }
     * 
     */
    public TicketInfo createTicketInfo() {
        return new TicketInfo();
    }

    /**
     * Create an instance of {@link AirlineInfo }
     * 
     */
    public AirlineInfo createAirlineInfo() {
        return new AirlineInfo();
    }

    /**
     * Create an instance of {@link AirlineList }
     * 
     */
    public AirlineList createAirlineList() {
        return new AirlineList();
    }

    /**
     * Create an instance of {@link BookingList }
     * 
     */
    public BookingList createBookingList() {
        return new BookingList();
    }

    /**
     * Create an instance of {@link DateList }
     * 
     */
    public DateList createDateList() {
        return new DateList();
    }

    /**
     * Create an instance of {@link BookingInfo }
     * 
     */
    public BookingInfo createBookingInfo() {
        return new BookingInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TicketInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/FlightDB", name = "tickets")
    public JAXBElement<TicketInfo> createTickets(TicketInfo value) {
        return new JAXBElement<TicketInfo>(_Tickets_QNAME, TicketInfo.class, null, value);
    }

}
