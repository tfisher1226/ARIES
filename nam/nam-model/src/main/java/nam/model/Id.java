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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Id complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Id">
 *   &lt;complexContent>
 *     &lt;extension base="{http://nam/model}Attribute">
 *       &lt;attribute name="source" type="{http://nam/model}IdSourceType" />
 *       &lt;attribute name="initialValue" type="{http://www.w3.org/2001/XMLSchema}long" default="1" />
 *       &lt;attribute name="allocationSize" type="{http://www.w3.org/2001/XMLSchema}long" default="1" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Id", namespace = "http://nam/model")
@XmlRootElement(name = "id", namespace = "http://nam/model")
public class Id
    extends Attribute
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    
    @XmlAttribute(name = "source")
    protected IdSourceType source = IdSourceType.IDENTITY;
    
    @XmlAttribute(name = "initialValue")
    protected Long initialValue;
    
    @XmlAttribute(name = "allocationSize")
    protected Long allocationSize;

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link IdSourceType }
     *     
     */
    public IdSourceType getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdSourceType }
     *     
     */
    public void setSource(IdSourceType value) {
        this.source = value;
    }

    /**
     * Gets the value of the initialValue property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInitialValue() {
        if (initialValue == null) {
            return  1L;
        } else {
            return initialValue;
        }
    }

    /**
     * Sets the value of the initialValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInitialValue(Long value) {
        this.initialValue = value;
    }

    /**
     * Gets the value of the allocationSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAllocationSize() {
        if (allocationSize == null) {
            return  1L;
        } else {
            return allocationSize;
        }
    }

    /**
     * Sets the value of the allocationSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAllocationSize(Long value) {
        this.allocationSize = value;
    }

}
