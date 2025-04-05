
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

    private final static QName _Weathers_QNAME = new QName("http://www.example.org/WeatherDB", "weathers");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.easyz.zhfw.pojo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WeatherList }
     * 
     */
    public WeatherList createWeatherList() {
        return new WeatherList();
    }

    /**
     * Create an instance of {@link WeatherInfo }
     * 
     */
    public WeatherInfo createWeatherInfo() {
        return new WeatherInfo();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDateInfo() {
        return new Date();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeatherList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/WeatherDB", name = "weathers")
    public JAXBElement<WeatherList> createWeathers(WeatherList value) {
        return new JAXBElement<WeatherList>(_Weathers_QNAME, WeatherList.class, null, value);
    }

}
