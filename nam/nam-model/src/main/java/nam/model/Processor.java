//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.11 at 12:10:41 PM PDT 
//


package nam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Processor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Processor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invocation" type="{http://nam/model}Invocation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="method" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Processor", namespace = "http://nam/model", propOrder = {
    "invocations"
})
@XmlRootElement(name = "processor", namespace = "http://nam/model")
public class Processor
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "invocation", namespace = "http://nam/model", required = true)
    protected List<Invocation> invocations;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "class", required = true)
    protected String clazz;
    @XmlAttribute(name = "method", required = true)
    protected String method;

    /**
     * Gets the value of the invocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Invocation }
     * 
     * 
     */
    public List<Invocation> getInvocations() {
        if (invocations == null) {
            invocations = new ArrayList<Invocation>();
        }
        return this.invocations;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethod(String value) {
        this.method = value;
    }

}
