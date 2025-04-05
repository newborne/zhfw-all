
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>WareHouse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="WareHouse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="items" type="{http://www.example.org/warehouse}ItemList"/>
 *         &lt;element name="holdingReques" type="{http://www.example.org/warehouse}HoldingRequestList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WareHouse", namespace = "http://www.example.org/warehouse", propOrder = {
    "items",
    "holdingReques"
})
public class WareHouse {

    @XmlElement(namespace = "http://www.example.org/warehouse", required = true)
    protected ItemList items;
    @XmlElement(namespace = "http://www.example.org/warehouse", required = true)
    protected HoldingRequestList holdingReques;

    /**
     * 获取items属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ItemList }
     *     
     */
    public ItemList getItems() {
        return items;
    }

    /**
     * 设置items属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ItemList }
     *     
     */
    public void setItems(ItemList value) {
        this.items = value;
    }

    /**
     * 获取holdingReques属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HoldingRequestList }
     *     
     */
    public HoldingRequestList getHoldingReques() {
        return holdingReques;
    }

    /**
     * 设置holdingReques属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HoldingRequestList }
     *     
     */
    public void setHoldingReques(HoldingRequestList value) {
        this.holdingReques = value;
    }

}
