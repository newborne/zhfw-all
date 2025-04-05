
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>WeatherType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="WeatherType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="sunny"/>
 *     &lt;enumeration value="cloudy"/>
 *     &lt;enumeration value="rainy"/>
 *     &lt;enumeration value="snow"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WeatherType", namespace = "http://www.example.org/WeatherDB")
@XmlEnum
public enum WeatherType {

    @XmlEnumValue("sunny")
    SUNNY("sunny"),
    @XmlEnumValue("cloudy")
    CLOUDY("cloudy"),
    @XmlEnumValue("rainy")
    RAINY("rainy"),
    @XmlEnumValue("snow")
    SNOW("snow");
    private final String value;

    WeatherType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WeatherType fromValue(String v) {
        for (WeatherType c: WeatherType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
