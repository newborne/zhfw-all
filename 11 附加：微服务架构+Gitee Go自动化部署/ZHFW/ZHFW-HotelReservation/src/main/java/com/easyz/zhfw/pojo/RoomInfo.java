
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RoomInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RoomInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomInfo", namespace = "http://www.example.org/HotelDB", propOrder = {
    "type",
    "totalAmount",
    "rate"
})
public class RoomInfo {

    @XmlElement(namespace = "http://www.example.org/HotelDB", required = true)
    protected String type;
    @XmlElement(namespace = "http://www.example.org/HotelDB")
    protected int totalAmount;
    @XmlElement(namespace = "http://www.example.org/HotelDB")
    protected int rate;

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
     * 获取totalAmount属性的值。
     * 
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置totalAmount属性的值。
     * 
     */
    public void setTotalAmount(int value) {
        this.totalAmount = value;
    }

    /**
     * 获取rate属性的值。
     * 
     */
    public int getRate() {
        return rate;
    }

    /**
     * 设置rate属性的值。
     * 
     */
    public void setRate(int value) {
        this.rate = value;
    }

}
