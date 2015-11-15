package org.aries.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailAddressList", namespace = "http://www.aries.org/common", propOrder = {
	"id",
	"name",
	"addresses"
})
@XmlRootElement(name = "emailAddressList", namespace = "http://www.aries.org/common")
public class EmailAddressList implements Comparable<EmailAddressList>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://www.aries.org/common")
	private Long id;
	
	@XmlElement(name = "name", namespace = "http://www.aries.org/common", required = true)
	private String name;
	
	@XmlElement(name = "addresses", namespace = "http://www.aries.org/common")
	private List<EmailAddress> addresses;
	
	
	public EmailAddressList() {
		addresses = new ArrayList<EmailAddress>();
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<EmailAddress> getAddresses() {
		synchronized (addresses) {
			return addresses;
		}
	}
	
	public void setAddresses(Collection<EmailAddress> emailAddressList) {
		if (emailAddressList == null) {
			this.addresses = null;
		} else {
		synchronized (this.addresses) {
			this.addresses = new ArrayList<EmailAddress>();
				addToAddresses(emailAddressList);
			}
		}
	}

	public void addToAddresses(EmailAddress emailAddress) {
		if (emailAddress != null ) {
			synchronized (this.addresses) {
				this.addresses.add(emailAddress);
			}
		}
	}

	public void addToAddresses(Collection<EmailAddress> emailAddressCollection) {
		if (emailAddressCollection != null && !emailAddressCollection.isEmpty()) {
			synchronized (this.addresses) {
				this.addresses.addAll(emailAddressCollection);
			}
		}
	}

	public void removeFromAddresses(EmailAddress emailAddress) {
		if (emailAddress != null ) {
			synchronized (this.addresses) {
				this.addresses.remove(emailAddress);
			}
		}
	}

	public void removeFromAddresses(Collection<EmailAddress> emailAddressCollection) {
		if (emailAddressCollection != null ) {
			synchronized (this.addresses) {
				this.addresses.removeAll(emailAddressCollection);
			}
		}
	}

	public void clearAddresses() {
		synchronized (addresses) {
			addresses.clear();
		}
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(EmailAddressList other) {
		int status = compare(name, other.name);
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
		EmailAddressList other = (EmailAddressList) object;
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
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "EmailAddressList: name="+name;
	}
	
}
