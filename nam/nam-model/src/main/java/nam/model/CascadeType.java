//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.11 at 12:10:41 PM PDT 
//


package nam.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CascadeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CascadeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="MERGE"/>
 *     &lt;enumeration value="DETACH"/>
 *     &lt;enumeration value="REFRESH"/>
 *     &lt;enumeration value="PERSIST"/>
 *     &lt;enumeration value="REMOVE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CascadeType", namespace = "http://nam/model")
@XmlEnum
public enum CascadeType {

    ALL,
    MERGE,
    DETACH,
    REFRESH,
    PERSIST,
    REMOVE;

    public String value() {
        return name();
    }

    public static CascadeType fromValue(String v) {
        return valueOf(v);
    }

}
