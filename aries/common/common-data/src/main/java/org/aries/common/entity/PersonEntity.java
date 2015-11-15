package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "Person")
@Table(name = "person")
@Cache(usage = READ_WRITE)
public class PersonEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "name_id", nullable = false)
	@ForeignKey(name = "person_name_fk", inverseName = "person_name_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PersonNameEntity name;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "phone_number_id")
	@ForeignKey(name = "person_phone_number_fk", inverseName = "person_phone_number_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PhoneNumberEntity phoneNumber;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "email_address_id")
	@ForeignKey(name = "person_email_address_fk", inverseName = "person_email_address_inverse_fk")
	@Cache(usage = READ_WRITE)
	private EmailAddressEntity emailAddress;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "street_address_id")
	@ForeignKey(name = "person_street_address_fk", inverseName = "person_street_address_inverse_fk")
	@Cache(usage = READ_WRITE)
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
	
	public void setName(PersonNameEntity personName) {
		this.name = personName;
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
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
