//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.11 at 12:10:41 PM PDT 
//


package nam.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.aries.adapter.BooleanAdapter;


/**
 * <p>Java class for Item complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Item">
 *   &lt;complexContent>
 *     &lt;extension base="{http://nam/model}Reference">
 *       &lt;attribute name="ascend" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="descend" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item", namespace = "http://nam/model")
@XmlRootElement(name = "item", namespace = "http://nam/model")
public class Item
    extends Reference
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "ascend")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean ascend;
    @XmlAttribute(name = "descend")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean descend;

    /**
     * Gets the value of the ascend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getAscend() {
        if (ascend == null) {
            return new BooleanAdapter().unmarshal("true");
        } else {
            return ascend;
        }
    }

    /**
     * Sets the value of the ascend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAscend(Boolean value) {
        this.ascend = value;
    }

    /**
     * Gets the value of the descend property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getDescend() {
        if (descend == null) {
            return new BooleanAdapter().unmarshal("false");
        } else {
            return descend;
        }
    }

    /**
     * Sets the value of the descend property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescend(Boolean value) {
        this.descend = value;
    }

}