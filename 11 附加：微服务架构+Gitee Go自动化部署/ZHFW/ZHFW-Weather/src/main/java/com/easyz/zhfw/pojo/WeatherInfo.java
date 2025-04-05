
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>WeatherInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="WeatherInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.example.org/WeatherDB}DateInfo"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.example.org/WeatherDB}WeatherType"/>
 *         &lt;element name="temperature" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeatherInfo", namespace = "http://www.example.org/WeatherDB", propOrder = {
    "date",
    "location",
    "description",
    "temperature"
})
public class WeatherInfo {

    @XmlElement(namespace = "http://www.example.org/WeatherDB", required = true)
    protected Date date;
    @XmlElement(namespace = "http://www.example.org/WeatherDB", required = true)
    protected String location;
    @XmlElement(namespace = "http://www.example.org/WeatherDB", required = true)
    @XmlSchemaType(name = "string")
    protected WeatherType description;
    @XmlElement(namespace = "http://www.example.org/WeatherDB")
    protected double temperature;

    /**
     * 获取date属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置date属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * 获取location属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置location属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WeatherType }
     *     
     */
    public WeatherType getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WeatherType }
     *     
     */
    public void setDescription(WeatherType value) {
        this.description = value;
    }

    /**
     * 获取temperature属性的值。
     * 
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * 设置temperature属性的值。
     * 
     */
    public void setTemperature(double value) {
        this.temperature = value;
    }

     // 构造方法、getter和setter省略

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "date=" + date +
                ", location='" + location + '\'' +
                // 其他属性的toString方法
                '}';
    }
}
