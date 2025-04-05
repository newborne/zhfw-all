
package com.easyz.zhfw.pojo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DateInfo的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="DateInfo">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Monday"/>
 *     &lt;enumeration value="Tuesday"/>
 *     &lt;enumeration value="Wednesday"/>
 *     &lt;enumeration value="Thursday"/>
 *     &lt;enumeration value="Friday"/>
 *     &lt;enumeration value="Saturday"/>
 *     &lt;enumeration value="Sunday"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DateInfo", namespace = "http://www.example.org/FlightDB")
@XmlEnum
public enum DateInfo {

    @XmlEnumValue("Monday")
    Monday("Monday"),
    @XmlEnumValue("Tuesday")
    Tuesday("Tuesday"),
    @XmlEnumValue("Wednesday")
    Wednesday("Wednesday"),
    @XmlEnumValue("Thursday")
    Thursday("Thursday"),
    @XmlEnumValue("Friday")
    Friday("Friday"),
    @XmlEnumValue("Saturday")
    Saturday("Saturday"),
    @XmlEnumValue("Sunday")
    Sunday("Sunday");
    private final String value;

    DateInfo(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DateInfo fromValue(String v) {
        for (DateInfo c: DateInfo.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
