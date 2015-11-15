package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@MappedSuperclass
public class AbstractUserEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//SEAM @UserPrincipal
	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;
	
	//SEAM @UserPassword(hash = "MD5")
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	///SEAM @PasswordSalt
	@Column(name = "password_salt")
	private String passwordSalt;
	
	//SEAM @UserFirstName
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	//SEAM @UserLastName
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "phone_number_id")
	@ForeignKey(name = "user_phone_number_fk", inverseName = "user_phone_number_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PhoneNumberEntity phoneNumber;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "email_address_id", unique = true)
	@ForeignKey(name = "user_email_address_fk", inverseName = "user_email_address_inverse_fk")
	@Cache(usage = READ_WRITE)
	private EmailAddressEntity emailAddress;
	
	//SEAM @UserEnabled
	@Column(name = "enabled")
	private Boolean enabled;
	
	
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
	
	public Boolean isEnabled() {
		return enabled;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] userId="+getUserId()+", emailAddress="+getEmailAddress();
		return "getClass().getSimpleName(): userId="+getUserId()+", emailAddress="+getEmailAddress();
	}
	
}
