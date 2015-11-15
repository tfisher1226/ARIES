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
 * <p>Java class for event-action.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="event-action">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="signed-in"/>
 *     &lt;enumeration value="signed-out"/>
 *     &lt;enumeration value="created"/>
 *     &lt;enumeration value="updated"/>
 *     &lt;enumeration value="deleted"/>
 *     &lt;enumeration value="removed"/>
 *     &lt;enumeration value="restored"/>
 *     &lt;enumeration value="flushed"/>
 *     &lt;enumeration value="reset"/>
 *     &lt;enumeration value="sent"/>
 *     &lt;enumeration value="notsent"/>
 *     &lt;enumeration value="received"/>
 *     &lt;enumeration value="logged"/>
 *     &lt;enumeration value="dropped"/>
 *     &lt;enumeration value="started"/>
 *     &lt;enumeration value="stopped"/>
 *     &lt;enumeration value="deployed"/>
 *     &lt;enumeration value="undeployed"/>
 *     &lt;enumeration value="configured"/>
 *     &lt;enumeration value="accessed"/>
 *     &lt;enumeration value="modified"/>
 *     &lt;enumeration value="committed"/>
 *     &lt;enumeration value="rolledback"/>
 *     &lt;enumeration value="approved"/>
 *     &lt;enumeration value="revoked"/>
 *     &lt;enumeration value="submitted"/>
 *     &lt;enumeration value="completed"/>
 *     &lt;enumeration value="cancelled"/>
 *     &lt;enumeration value="aborted"/>
 *     &lt;enumeration value="failed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "event-action", namespace = "http://www.aries.org/event/0.0.1")
@XmlEnum
public enum EventAction {

    @XmlEnumValue("signed-in")
    SIGNED_IN("signed-in"),
    @XmlEnumValue("signed-out")
    SIGNED_OUT("signed-out"),
    @XmlEnumValue("created")
    CREATED("created"),
    @XmlEnumValue("updated")
    UPDATED("updated"),
    @XmlEnumValue("deleted")
    DELETED("deleted"),
    @XmlEnumValue("removed")
    REMOVED("removed"),
    @XmlEnumValue("restored")
    RESTORED("restored"),
    @XmlEnumValue("flushed")
    FLUSHED("flushed"),
    @XmlEnumValue("reset")
    RESET("reset"),
    @XmlEnumValue("sent")
    SENT("sent"),
    @XmlEnumValue("notsent")
    NOTSENT("notsent"),
    @XmlEnumValue("received")
    RECEIVED("received"),
    @XmlEnumValue("logged")
    LOGGED("logged"),
    @XmlEnumValue("dropped")
    DROPPED("dropped"),
    @XmlEnumValue("started")
    STARTED("started"),
    @XmlEnumValue("stopped")
    STOPPED("stopped"),
    @XmlEnumValue("deployed")
    DEPLOYED("deployed"),
    @XmlEnumValue("undeployed")
    UNDEPLOYED("undeployed"),
    @XmlEnumValue("configured")
    CONFIGURED("configured"),
    @XmlEnumValue("accessed")
    ACCESSED("accessed"),
    @XmlEnumValue("modified")
    MODIFIED("modified"),
    @XmlEnumValue("committed")
    COMMITTED("committed"),
    @XmlEnumValue("rolledback")
    ROLLEDBACK("rolledback"),
    @XmlEnumValue("approved")
    APPROVED("approved"),
    @XmlEnumValue("revoked")
    REVOKED("revoked"),
    @XmlEnumValue("submitted")
    SUBMITTED("submitted"),
    @XmlEnumValue("completed")
    COMPLETED("completed"),
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled"),
    @XmlEnumValue("aborted")
    ABORTED("aborted"),
    @XmlEnumValue("failed")
    FAILED("failed");
    private final String value;

    EventAction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventAction fromValue(String v) {
        for (EventAction c: EventAction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
