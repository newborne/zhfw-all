
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>BookingInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="BookingInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bookingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="seats" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date" type="{http://www.example.org/FlightDB}DateInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingInfo", namespace = "http://www.example.org/FlightDB", propOrder = {
    "bookingID",
    "flightNo",
    "seats",
    "date"
})
public class BookingInfo {

    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected String bookingID;
    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected String flightNo;
    @XmlElement(namespace = "http://www.example.org/FlightDB")
    protected int seats;
    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    @XmlSchemaType(name = "string")
    protected DateInfo date;

    /**
     * 获取bookingID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingID() {
        return bookingID;
    }

    /**
     * 设置bookingID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingID(String value) {
        this.bookingID = value;
    }

    /**
     * 获取flightNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 设置flightNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightNo(String value) {
        this.flightNo = value;
    }

    /**
     * 获取seats属性的值。
     * 
     */
    public int getSeats() {
        return seats;
    }

    /**
     * 设置seats属性的值。
     * 
     */
    public void setSeats(int value) {
        this.seats = value;
    }

    /**
     * 获取date属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DateInfo }
     *     
     */
    public DateInfo getDate() {
        return date;
    }

    /**
     * 设置date属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DateInfo }
     *     
     */
    public void setDate(DateInfo value) {
        this.date = value;
    }

}
