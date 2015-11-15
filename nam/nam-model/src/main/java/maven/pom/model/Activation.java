//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.11 at 12:10:41 PM PDT 
//


package maven.pom.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.aries.adapter.BooleanAdapter;


/**
 * 
 * 				The conditions within the build runtime environment which will trigger
 * 				the automatic inclusion
 * 				of the build profile.
 *       
 * 
 * <p>Java class for Activation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Activation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="activeByDefault" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="jdk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="os" type="{http://maven.apache.org/POM/4.0.0}ActivationOS" minOccurs="0"/>
 *         &lt;element name="property" type="{http://maven.apache.org/POM/4.0.0}ActivationProperty" minOccurs="0"/>
 *         &lt;element name="file" type="{http://maven.apache.org/POM/4.0.0}ActivationFile" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Activation", namespace = "http://maven.apache.org/POM/4.0.0", propOrder = {

})
public class Activation
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0", type = String.class, defaultValue = "false")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean activeByDefault;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected String jdk;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected ActivationOS os;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected ActivationProperty property;
    @XmlElement(namespace = "http://maven.apache.org/POM/4.0.0")
    protected ActivationFile file;

    /**
     * Gets the value of the activeByDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getActiveByDefault() {
        return activeByDefault;
    }

    /**
     * Sets the value of the activeByDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveByDefault(Boolean value) {
        this.activeByDefault = value;
    }

    /**
     * Gets the value of the jdk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdk() {
        return jdk;
    }

    /**
     * Sets the value of the jdk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdk(String value) {
        this.jdk = value;
    }

    /**
     * Gets the value of the os property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationOS }
     *     
     */
    public ActivationOS getOs() {
        return os;
    }

    /**
     * Sets the value of the os property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationOS }
     *     
     */
    public void setOs(ActivationOS value) {
        this.os = value;
    }

    /**
     * Gets the value of the property property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationProperty }
     *     
     */
    public ActivationProperty getProperty() {
        return property;
    }

    /**
     * Sets the value of the property property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationProperty }
     *     
     */
    public void setProperty(ActivationProperty value) {
        this.property = value;
    }

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     {@link ActivationFile }
     *     
     */
    public ActivationFile getFile() {
        return file;
    }

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActivationFile }
     *     
     */
    public void setFile(ActivationFile value) {
        this.file = value;
    }

}
