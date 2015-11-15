package org.aries.common.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.aries.common.entity.EmailBoxEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "EmailAccount")
@Table(name = "email_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailAccountEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private String userId;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<EmailBoxEntity> emailBoxes;

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

	public List<EmailBoxEntity> getEmailBoxes() {
		if (emailBoxes == null)
			emailBoxes = new ArrayList<EmailBoxEntity>();
		return emailBoxes;
	}

	public void setEmailBoxes(List<EmailBoxEntity> emailBoxes) {
		this.emailBoxes = emailBoxes;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		EmailAccountEntity other = (EmailAccountEntity) object;
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