
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HotelInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HotelInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rooms" type="{http://www.example.org/HotelDB}RoomList"/>
 *         &lt;element name="bookings" type="{http://www.example.org/HotelDB}BookingList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelInfo", namespace = "http://www.example.org/HotelDB", propOrder = {
    "rooms",
    "bookings"
})
public class HotelInfo {

    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected RoomList rooms;
    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected BookingList bookings;

    /**
     * 获取rooms属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RoomList }
     *     
     */
    public RoomList getRooms() {
        return rooms;
    }

    /**
     * 设置rooms属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RoomList }
     *     
     */
    public void setRooms(RoomList value) {
        this.rooms = value;
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
