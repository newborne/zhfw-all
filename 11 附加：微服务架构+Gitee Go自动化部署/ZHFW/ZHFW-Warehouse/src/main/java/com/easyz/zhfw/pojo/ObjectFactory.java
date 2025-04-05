
package com.easyz.zhfw.pojo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.easyz.zhfw.pojo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Warehouse_QNAME = new QName("http://www.example.org/warehouse", "warehouse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyz.zhfw.pojo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WareHouse }
     * 
     */
    public WareHouse createWareHouse() {
        return new WareHouse();
    }

    /**
     * Create an instance of {@link HoldingRequestList }
     * 
     */
    public HoldingRequestList createHoldingRequestList() {
        return new HoldingRequestList();
    }

    /**
     * Create an instance of {@link ItemList }
     * 
     */
    public ItemList createItemList() {
        return new ItemList();
    }

    /**
     * Create an instance of {@link HoldingRequestInfo }
     * 
     */
    public HoldingRequestInfo createHoldingRequestInfo() {
        return new HoldingRequestInfo();
    }

    /**
     * Create an instance of {@link ItemInfo }
     * 
     */
    public ItemInfo createItemInfo() {
        return new ItemInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WareHouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/warehouse", name = "warehouse")
    public JAXBElement<WareHouse> createWarehouse(WareHouse value) {
        return new JAXBElement<WareHouse>(_Warehouse_QNAME, WareHouse.class, null, value);
    }

}
