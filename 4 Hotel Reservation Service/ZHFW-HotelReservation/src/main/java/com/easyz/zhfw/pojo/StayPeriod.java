
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>stayPeriodType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="stayPeriodType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="checkin" type="{http://www.example.org/HotelDB}DateType"/>
 *         &lt;element name="checkout" type="{http://www.example.org/HotelDB}DateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stayPeriodType", namespace = "http://www.example.org/HotelDB", propOrder = {
    "checkin",
    "checkout"
})
public class StayPeriod {

    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected DateType checkin;
    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected DateType checkout;

    /**
     * 获取checkin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getCheckin() {
        return checkin;
    }

    /**
     * 设置checkin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setCheckin(DateType value) {
        this.checkin = value;
    }

    /**
     * 获取checkout属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getCheckout() {
        return checkout;
    }

    /**
     * 设置checkout属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setCheckout(DateType value) {
        this.checkout = value;
    }

}
