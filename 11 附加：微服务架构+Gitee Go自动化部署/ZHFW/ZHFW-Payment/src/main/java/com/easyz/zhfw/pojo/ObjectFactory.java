
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

    private final static QName _Bank_QNAME = new QName("http://www.example.org/bankDB", "bank");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyz.zhfw.pojo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BanKInfo }
     * 
     */
    public BanKInfo createBanKInfo() {
        return new BanKInfo();
    }

    /**
     * Create an instance of {@link AccountInfo }
     * 
     */
    public AccountInfo createAccountInfo() {
        return new AccountInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BanKInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/bankDB", name = "bank")
    public JAXBElement<BanKInfo> createBank(BanKInfo value) {
        return new JAXBElement<BanKInfo>(_Bank_QNAME, BanKInfo.class, null, value);
    }

}
