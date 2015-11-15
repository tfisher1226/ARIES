package org.aries.common;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailAddress", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"url",
	"userId",
	"firstName",
	"lastName",
	"phoneNumber",
	"organization",
	"creationDate",
	"lastUpdate",
	"enabled"
})
@XmlRootElement(name = "emailAddress", namespace = "http://www.aries.org/common")
public class EmailAddress implements Comparable<EmailAddress>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "url", namespace = "http://www.aries.org/common", required = true)
	private String url;
	
	@XmlElement(name = "userId", namespace = "http://www.aries.org/common")
	private String userId;
	
	@XmlElement(name = "firstName", namespace = "http://www.aries.org/common")
	private String firstName;
	
	@XmlElement(name = "lastName", namespace = "http://www.aries.org/common")
	private String lastName;
	
	@XmlElement(name = "phoneNumber", namespace = "http://www.aries.org/common")
	private PhoneNumber phoneNumber;
	
	@XmlElement(name = "organization", namespace = "http://www.aries.org/common")
	private String organization;
	
	@XmlElement(name = "creationDate", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	@XmlElement(name = "enabled", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled;
	
	
	public EmailAddress() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Boolean isEnabled() {
		return enabled != null && enabled;
	}
	
	public Boolean getEnabled() {
		return enabled != null && enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(EmailAddress other) {
		int status = compare(url, other.url);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EmailAddress other = (EmailAddress) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (url != null)
			hashCode += url.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "EmailAddress: url="+url;
	}
	
}
