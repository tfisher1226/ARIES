package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonName", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"lastName",
	"firstName",
	"middleInitial"
})
@XmlRootElement(name = "personName", namespace = "http://www.aries.org/common")
public class PersonName implements Comparable<PersonName>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "lastName", namespace = "http://www.aries.org/common", required = true)
	private String lastName;
	
	@XmlElement(name = "firstName", namespace = "http://www.aries.org/common", required = true)
	private String firstName;
	
	@XmlElement(name = "middleInitial", namespace = "http://www.aries.org/common")
	private String middleInitial;
	
	
	public PersonName() {
		//nothing for now
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleInitial() {
		return middleInitial;
	}
	
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(PersonName other) {
		int status = compare(lastName, other.lastName);
		if (status != 0)
			return status;
		status = compare(firstName, other.firstName);
		if (status != 0)
			return status;
		status = compare(middleInitial, other.middleInitial);
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
		PersonName other = (PersonName) object;
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
		if (lastName != null)
			hashCode += lastName.hashCode();
		if (firstName != null)
			hashCode += firstName.hashCode();
		if (middleInitial != null)
			hashCode += middleInitial.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		return "PersonName: lastName="+lastName+", firstName="+firstName+", middleInitial="+middleInitial;
	}
	
}
