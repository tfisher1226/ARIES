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
import org.aries.common.Property;


/**
 * <p>Java class for Security complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Security">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="realm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.aries.org/common}property" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="runAs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rolesAllowed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="moduleName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="useCallerIdentity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="callbackHandler" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Security", namespace = "http://nam/model", propOrder = {
    "user",
    "password",
    "realm",
    "properties"
})
@XmlRootElement(name = "security", namespace = "http://nam/model")
public class Security
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://nam/model", required = true)
    protected String user;
    @XmlElement(namespace = "http://nam/model", required = true)
    protected String password;
    @XmlElement(namespace = "http://nam/model")
    protected String realm;
    @XmlElement(name = "property", namespace = "http://www.aries.org/common")
    protected List<Property> properties;
    @XmlAttribute(name = "runAs")
    protected String runAs;
    @XmlAttribute(name = "rolesAllowed")
    protected String rolesAllowed;
    @XmlAttribute(name = "moduleName")
    protected String moduleName;
    @XmlAttribute(name = "useCallerIdentity")
    protected String useCallerIdentity;
    @XmlAttribute(name = "callbackHandler")
    protected String callbackHandler;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the realm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealm() {
        return realm;
    }

    /**
     * Sets the value of the realm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealm(String value) {
        this.realm = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Property }
     * 
     * 
     */
    public List<Property> getProperties() {
        if (properties == null) {
            properties = new ArrayList<Property>();
        }
        return this.properties;
    }

    /**
     * Gets the value of the runAs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunAs() {
        return runAs;
    }

    /**
     * Sets the value of the runAs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunAs(String value) {
        this.runAs = value;
    }

    /**
     * Gets the value of the rolesAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolesAllowed() {
        return rolesAllowed;
    }

    /**
     * Sets the value of the rolesAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolesAllowed(String value) {
        this.rolesAllowed = value;
    }

    /**
     * Gets the value of the moduleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets the value of the moduleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleName(String value) {
        this.moduleName = value;
    }

    /**
     * Gets the value of the useCallerIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseCallerIdentity() {
        return useCallerIdentity;
    }

    /**
     * Sets the value of the useCallerIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseCallerIdentity(String value) {
        this.useCallerIdentity = value;
    }

    /**
     * Gets the value of the callbackHandler property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallbackHandler() {
        return callbackHandler;
    }

    /**
     * Sets the value of the callbackHandler property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallbackHandler(String value) {
        this.callbackHandler = value;
    }

}