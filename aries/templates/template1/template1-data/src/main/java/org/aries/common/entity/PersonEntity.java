package org.aries.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.PersonNameEntity;
import org.aries.common.entity.PhoneNumberEntity;
import org.aries.common.entity.StreetAddressEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Person")
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private String userId;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PersonNameEntity name;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity phoneNumber;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailAddressEntity emailAddress;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private StreetAddressEntity streetAddress;


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

	public PersonNameEntity getName() {
		return name;
	}

	public void setName(PersonNameEntity name) {
		this.name = name;
	}

	public PhoneNumberEntity getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumberEntity phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EmailAddressEntity getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddressEntity emailAddress) {
		this.emailAddress = emailAddress;
	}

	public StreetAddressEntity getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(StreetAddressEntity streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		PersonEntity other = (PersonEntity) object;
		if (this.getId() == null || other.getId() == null || 
			this.getUserId() == null || other.getUserId() == null)
			return this == other;
		if (this.getId().equals(other.getId()) && 
			this.getUserId().equals(other.getUserId()))
				return true;
		return false;
	}

	@Override
	public int hashCode() {
		if (getId() != null)
			return getId().hashCode();
		return 0;
	}

}