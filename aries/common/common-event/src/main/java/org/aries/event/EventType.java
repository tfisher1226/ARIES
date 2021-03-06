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
 * <p>Java class for event-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="event-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="application"/>
 *     &lt;enumeration value="server"/>
 *     &lt;enumeration value="cache"/>
 *     &lt;enumeration value="data"/>
 *     &lt;enumeration value="message"/>
 *     &lt;enumeration value="record"/>
 *     &lt;enumeration value="process"/>
 *     &lt;enumeration value="notification"/>
 *     &lt;enumeration value="operation"/>
 *     &lt;enumeration value="user"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "event-type", namespace = "http://www.aries.org/event/0.0.1")
@XmlEnum
public enum EventType {

    @XmlEnumValue("application")
    APPLICATION("application"),
    @XmlEnumValue("server")
    SERVER("server"),
    @XmlEnumValue("cache")
    CACHE("cache"),
    @XmlEnumValue("data")
    DATA("data"),
    @XmlEnumValue("message")
    MESSAGE("message"),
    @XmlEnumValue("record")
    RECORD("record"),
    @XmlEnumValue("process")
    PROCESS("process"),
    @XmlEnumValue("notification")
    NOTIFICATION("notification"),
    @XmlEnumValue("operation")
    OPERATION("operation"),
    @XmlEnumValue("user")
    USER("user");
    private final String value;

    EventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventType fromValue(String v) {
        for (EventType c: EventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
