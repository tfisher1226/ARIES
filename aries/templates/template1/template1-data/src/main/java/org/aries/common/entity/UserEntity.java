package org.aries.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.PhoneNumberEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "User")
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Id
	@GeneratedValue
	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "enabled")
	private Boolean enabled;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity phoneNumber;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailAddressEntity emailAddress;


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

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		UserEntity other = (UserEntity) object;
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