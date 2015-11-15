//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.07.02 at 01:27:25 PM PDT 
//


package org.aries.event;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for event-severity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="event-severity">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="info"/>
 *     &lt;enumeration value="event"/>
 *     &lt;enumeration value="warning"/>
 *     &lt;enumeration value="error"/>
 *     &lt;enumeration value="fatal"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "event-severity", namespace = "http://www.aries.org/event/0.0.1")
@XmlEnum
public enum EventSeverity {

    @XmlEnumValue("info")
    INFO("info"),
    @XmlEnumValue("event")
    EVENT("event"),
    @XmlEnumValue("warning")
    WARNING("warning"),
    @XmlEnumValue("error")
    ERROR("error"),
    @XmlEnumValue("fatal")
    FATAL("fatal");
    private final String value;

    EventSeverity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventSeverity fromValue(String v) {
        for (EventSeverity c: EventSeverity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
