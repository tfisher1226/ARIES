package org.aries.common.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "EmailAccount")
@Table(name = "email_account", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@Cache(usage = READ_WRITE)
public class EmailAccountEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;
	
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(name = "password_salt")
	private String passwordSalt;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "email_account_email_boxe", joinColumns = @JoinColumn(name = "email_account_id"), inverseJoinColumns = @JoinColumn(name = "email_boxe_id"))
	@ForeignKey(name = "email_account_email_boxe_fk", inverseName = "email_account_email_boxe_inverse_fk")
	@Cache(usage = READ_WRITE)
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
	
	public List<EmailBoxEntity> getEmailBoxes() {
		if (emailBoxes == null)
			emailBoxes = new ArrayList<EmailBoxEntity>();
		return emailBoxes;
	}
	
	public void setEmailBoxes(Collection<EmailBoxEntity> emailBoxList) {
		if (emailBoxList == null) {
			this.emailBoxes = null;
		} else {
			this.emailBoxes = new ArrayList<EmailBoxEntity>();
			this.emailBoxes.addAll(emailBoxList);
		}
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
			return getClass().getSimpleName()+"["+getId()+"] userId="+getUserId();
		return "getClass().getSimpleName(): userId="+getUserId();
	}
	
}
