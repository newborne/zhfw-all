
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TicketInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="TicketInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="airlines" type="{http://www.example.org/FlightDB}AirlineList"/>
 *         &lt;element name="bookings" type="{http://www.example.org/FlightDB}BookingList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TicketInfo", namespace = "http://www.example.org/FlightDB", propOrder = {
    "airlines",
    "bookings"
})
public class TicketInfo {

    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected AirlineList airlines;
    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected BookingList bookings;

    /**
     * 获取airlines属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AirlineList }
     *     
     */
    public AirlineList getAirlines() {
        return airlines;
    }

    /**
     * 设置airlines属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AirlineList }
     *     
     */
    public void setAirlines(AirlineList value) {
        this.airlines = value;
    }

    /**
     * 获取bookings属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BookingList }
     *     
     */
    public BookingList getBookings() {
        return bookings;
    }

    /**
     * 设置bookings属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BookingList }
     *     
     */
    public void setBookings(BookingList value) {
        this.bookings = value;
    }

}
