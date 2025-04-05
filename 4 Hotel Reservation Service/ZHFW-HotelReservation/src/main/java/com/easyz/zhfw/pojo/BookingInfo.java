
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="stayPeriod" type="{http://www.example.org/HotelDB}stayPeriodType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookingInfo", namespace = "http://www.example.org/HotelDB", propOrder = {
    "bookingID",
    "type",
    "amount",
    "stayPeriod"
})
public class BookingInfo {

    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected String bookingID;
    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected String type;
    @XmlElement(namespace = "http://www.example.org/HotelDB")
    protected int amount;
    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected StayPeriod stayPeriod;

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
     * 获取type属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * 获取amount属性的值。
     * 
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 设置amount属性的值。
     * 
     */
    public void setAmount(int value) {
        this.amount = value;
    }

    /**
     * 获取stayPeriod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link StayPeriod }
     *     
     */
    public StayPeriod getStayPeriod() {
        return stayPeriod;
    }

    /**
     * 设置stayPeriod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link StayPeriod }
     *     
     */
    public void setStayPeriod(StayPeriod value) {
        this.stayPeriod = value;
    }

}
