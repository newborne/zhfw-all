
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DateInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DateInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="year" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="month" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Date", namespace = "http://www.example.org/WeatherDB", propOrder = {
    "year",
    "month",
    "date"
})
public class Date {

    @XmlElement(namespace = "http://www.example.org/WeatherDB")
    protected int year;
    @XmlElement(namespace = "http://www.example.org/WeatherDB")
    protected int month;
    @XmlElement(namespace = "http://www.example.org/WeatherDB")
    protected int date;

    /**
     * 获取year属性的值。
     * 
     */
    public int getYear() {
        return year;
    }

    /**
     * 设置year属性的值。
     * 
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * 获取month属性的值。
     * 
     */
    public int getMonth() {
        return month;
    }

    /**
     * 设置month属性的值。
     * 
     */
    public void setMonth(int value) {
        this.month = value;
    }

    /**
     * 获取date属性的值。
     * 
     */
    public int getDate() {
        return date;
    }

    /**
     * 设置date属性的值。
     * 
     */
    public void setDate(int value) {
        this.date = value;
    }

    // 构造方法、getter和setter省略

    @Override
    public String toString() {
        return "Date{" +
                "year=" + year +
                ", month=" + month +
                ", date=" + date +
                '}';
    }

}
