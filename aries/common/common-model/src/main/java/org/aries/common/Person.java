package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"userId",
	"name",
	"phoneNumber",
	"emailAddress",
	"streetAddress"
})
@XmlRootElement(name = "person", namespace = "http://www.aries.org/common")
public class Person implements Comparable<Person>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "userId", namespace = "http://www.aries.org/common", required = true)
	private String userId;
	
	@XmlElement(name = "name", namespace = "http://www.aries.org/common", required = true)
	private PersonName name;
	
	@XmlElement(name = "phoneNumber", namespace = "http://www.aries.org/common")
	private PhoneNumber phoneNumber;
	
	@XmlElement(name = "emailAddress", namespace = "http://www.aries.org/common")
	private EmailAddress emailAddress;
	
	@XmlElement(name = "streetAddress", namespace = "http://www.aries.org/common")
	private StreetAddress streetAddress;
	
	
	public Person() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public PersonName getName() {
		return name;
	}
	
	public void setName(PersonName personName) {
		this.name = personName;
	}
	
	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public StreetAddress getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(StreetAddress streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Person other) {
		int status = compare(userId, other.userId);
		if (status != 0)
			return status;
		status = compare(name, other.name);
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
		Person other = (Person) object;
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
		if (userId != null)
			hashCode += userId.hashCode();
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Person: userId="+userId+", name="+name;
	}
	
}
