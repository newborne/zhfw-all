
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AirlineInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AirlineInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flightNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="departure" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="seats" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dates" type="{http://www.example.org/FlightDB}DateList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirlineInfo", namespace = "http://www.example.org/FlightDB", propOrder = {
    "flightNo",
    "departure",
    "destination",
    "seats",
    "price",
    "dates"
})
public class AirlineInfo {

    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected String flightNo;
    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected String departure;
    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected String destination;
    @XmlElement(namespace = "http://www.example.org/FlightDB")
    protected int seats;
    @XmlElement(namespace = "http://www.example.org/FlightDB")
    protected int price;
    @XmlElement(namespace = "http://www.example.org/FlightDB", required = true)
    protected DateList dates;

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
     * 获取departure属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeparture() {
        return departure;
    }

    /**
     * 设置departure属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeparture(String value) {
        this.departure = value;
    }

    /**
     * 获取destination属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * 设置destination属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
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
     * 获取price属性的值。
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * 设置price属性的值。
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * 获取dates属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DateList }
     *     
     */
    public DateList getDates() {
        return dates;
    }

    /**
     * 设置dates属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DateList }
     *     
     */
    public void setDates(DateList value) {
        this.dates = value;
    }

}
