package org.aries.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractUser", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"userId",
	"password",
	"password2",
	"firstName",
	"lastName",
	"phoneNumber",
	"emailAddress",
	"enabled"
})
@XmlRootElement(name = "abstractUser", namespace = "http://www.aries.org/common")
public abstract class AbstractUser implements Comparable<AbstractUser>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "userId", namespace = "http://www.aries.org/common", required = true)
	private String userId;
	
	@XmlElement(name = "password", namespace = "http://www.aries.org/common", required = true)
	private String password;
	
	@XmlElement(name = "password2", namespace = "http://www.aries.org/common", required = true)
	private String password2;
	
	@XmlElement(name = "firstName", namespace = "http://www.aries.org/common", required = true)
	private String firstName;
	
	@XmlElement(name = "lastName", namespace = "http://www.aries.org/common", required = true)
	private String lastName;
	
	@XmlElement(name = "phoneNumber", namespace = "http://www.aries.org/common")
	private PhoneNumber phoneNumber;
	
	@XmlElement(name = "emailAddress", namespace = "http://www.aries.org/common")
	private EmailAddress emailAddress;
	
	@XmlElement(name = "enabled", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled;
	
	
	public AbstractUser() {
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public void setPassword2(String password2) {
		this.password2 = password2;
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
	
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
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
	public int compareTo(AbstractUser other) {
		int status = compare(userId, other.userId);
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
		AbstractUser other = (AbstractUser) object;
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
		if (emailAddress != null)
			hashCode += emailAddress.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getName()+": ");
		buf.append("hashCode="+hashCode());
		if (userId != null)
			buf.append(", userId="+userId);
		if (emailAddress != null)
			buf.append(", emailAddress="+emailAddress);
		return buf.toString();
	}
	
}
