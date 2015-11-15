package org.aries.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "EmailAddressList")
@Table(name = "email_address_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailAddressListEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "email_address", nullable = false)
	private String emailAddress;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EmailAddressListEntity other = (EmailAddressListEntity) object;
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}