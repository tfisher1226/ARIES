//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.13 at 09:55:44 AM PST 
//


package org.aries.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.aries.adapter.BooleanAdapter;
import org.aries.common.EmailAccount;
import org.aries.common.EmailAddress;
import org.aries.common.PhoneNumber;
import org.aries.common.StreetAddress;


/**
 * <p>Java class for User complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="User">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailAccount" type="{http://www.aries.org/common}EmailAccount" minOccurs="0"/>
 *         &lt;element name="emailAddress" type="{http://www.aries.org/common}EmailAddress" minOccurs="0"/>
 *         &lt;element name="streetAddress" type="{http://www.aries.org/common}StreetAddress" minOccurs="0"/>
 *         &lt;element name="cellPhone" type="{http://www.aries.org/common}PhoneNumber" minOccurs="0"/>
 *         &lt;element name="homePhone" type="{http://www.aries.org/common}PhoneNumber" minOccurs="0"/>
 *         &lt;element name="userPermissions" type="{http://www.aries.org/security}UserPermission" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = {
    "id",
    "userId",
    "password",
    "enabled",
    "firstName",
    "lastName",
    "emailAccount",
    "emailAddress",
    "streetAddress",
    "cellPhone",
    "homePhone",
    "userPermissions"
})
@XmlRootElement(name = "user")
public class User
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Long id;
    protected String userId;
    protected String password;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    @XmlSchemaType(name = "boolean")
    protected Boolean enabled;
    protected String firstName;
    protected String lastName;
    protected EmailAccount emailAccount;
    protected EmailAddress emailAddress;
    protected StreetAddress streetAddress;
    protected PhoneNumber cellPhone;
    protected PhoneNumber homePhone;
    @XmlElement(nillable = true)
    protected List<UserPermission> userPermissions;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
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
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the emailAccount property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAccount }
     *     
     */
    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    /**
     * Sets the value of the emailAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAccount }
     *     
     */
    public void setEmailAccount(EmailAccount value) {
        this.emailAccount = value;
    }

    /**
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link EmailAddress }
     *     
     */
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailAddress }
     *     
     */
    public void setEmailAddress(EmailAddress value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the streetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link StreetAddress }
     *     
     */
    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the value of the streetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreetAddress }
     *     
     */
    public void setStreetAddress(StreetAddress value) {
        this.streetAddress = value;
    }

    /**
     * Gets the value of the cellPhone property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getCellPhone() {
        return cellPhone;
    }

    /**
     * Sets the value of the cellPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setCellPhone(PhoneNumber value) {
        this.cellPhone = value;
    }

    /**
     * Gets the value of the homePhone property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getHomePhone() {
        return homePhone;
    }

    /**
     * Sets the value of the homePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setHomePhone(PhoneNumber value) {
        this.homePhone = value;
    }

    /**
     * Gets the value of the userPermissions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userPermissions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserPermissions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserPermission }
     * 
     * 
     */
    public List<UserPermission> getUserPermissions() {
        if (userPermissions == null) {
            userPermissions = new ArrayList<UserPermission>();
        }
        return this.userPermissions;
    }

}