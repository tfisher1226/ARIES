package org.aries.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailAccount", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"userId",
	"passwordHash",
	"passwordSalt",
	"firstName",
	"lastName",
	"emailBoxes",
	"enabled"
})
@XmlRootElement(name = "emailAccount", namespace = "http://www.aries.org/common")
public class EmailAccount implements Comparable<EmailAccount>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "userId", namespace = "http://www.aries.org/common", required = true)
	private String userId;
	
	@XmlElement(name = "passwordHash", namespace = "http://www.aries.org/common", required = true)
	private String passwordHash;
	
	@XmlElement(name = "passwordSalt", namespace = "http://www.aries.org/common")
	private String passwordSalt;
	
	@XmlElement(name = "firstName", namespace = "http://www.aries.org/common")
	private String firstName;
	
	@XmlElement(name = "lastName", namespace = "http://www.aries.org/common")
	private String lastName;
	
	@XmlElement(name = "emailBoxes", namespace = "http://www.aries.org/common", nillable = true)
	private List<EmailBox> emailBoxes;
	
	@XmlElement(name = "enabled", namespace = "http://www.aries.org/common", type = String.class)
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled;
	
	
	public EmailAccount() {
		emailBoxes = new ArrayList<EmailBox>();
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
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordSalt() {
		return passwordSalt;
	}
	
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
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
	
	public List<EmailBox> getEmailBoxes() {
		synchronized (emailBoxes) {
			return emailBoxes;
		}
	}
	
	public void setEmailBoxes(Collection<EmailBox> emailBoxList) {
		if (emailBoxList == null) {
			this.emailBoxes = null;
		} else {
			synchronized (this.emailBoxes) {
				this.emailBoxes = new ArrayList<EmailBox>();
				addToEmailBoxes(emailBoxList);
			}
		}
	}

	public void addToEmailBoxes(EmailBox emailBox) {
		if (emailBox != null ) {
			synchronized (this.emailBoxes) {
				this.emailBoxes.add(emailBox);
			}
		}
	}

	public void addToEmailBoxes(Collection<EmailBox> emailBoxCollection) {
		if (emailBoxCollection != null && !emailBoxCollection.isEmpty()) {
			synchronized (this.emailBoxes) {
				this.emailBoxes.addAll(emailBoxCollection);
			}
		}
	}

	public void removeFromEmailBoxes(EmailBox emailBox) {
		if (emailBox != null ) {
			synchronized (this.emailBoxes) {
				this.emailBoxes.remove(emailBox);
			}
		}
	}

	public void removeFromEmailBoxes(Collection<EmailBox> emailBoxCollection) {
		if (emailBoxCollection != null ) {
			synchronized (this.emailBoxes) {
				this.emailBoxes.removeAll(emailBoxCollection);
			}
		}
	}

	public void clearEmailBoxes() {
		synchronized (emailBoxes) {
			emailBoxes.clear();
		}
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
	public int compareTo(EmailAccount other) {
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
		EmailAccount other = (EmailAccount) object;
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
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "EmailAccount: userId="+userId;
	}
	
}
