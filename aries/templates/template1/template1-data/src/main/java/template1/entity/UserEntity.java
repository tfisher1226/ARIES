package template1.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.PhoneNumberEntity;
import org.aries.common.entity.StreetAddressEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.jboss.seam.annotations.security.management.PasswordSalt;
import org.jboss.seam.annotations.security.management.UserEnabled;
import org.jboss.seam.annotations.security.management.UserFirstName;
import org.jboss.seam.annotations.security.management.UserLastName;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;


@Entity(name = "User")
@Table(name = "user", 
	uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@UserEnabled
	@Column(name = "enabled")
	private boolean enabled;

	@UserPrincipal
	@Column(name = "user_id", nullable=false, unique=true)
	private String userId;

	@UserPassword(hash="MD5")
	//@UserPassword(hash = "SHA")
	@Column(name = "password", nullable=false)
	private String password;

	@PasswordSalt
	@Column(name = "password_salt")
	private String passwordSalt;

	@UserFirstName
	@Column(name = "first_name")
	private String firstName;

	@UserLastName
	@Column(name = "last_name")
	private String lastName;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "email_address_id")
	@ForeignKey(name = "user_email_address_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private EmailAddressEntity emailAddress;

//	@OneToOne(cascade = {CascadeType.ALL})
//	@JoinColumn(name = "email_account_id")
//	@ForeignKey(name = "user_email_account_fk")
//	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//	private EmailAccountEntity emailAccount;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "home_phone_id")
	@ForeignKey(name = "user_home_phone_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity homePhone;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "cell_phone_id")
	@ForeignKey(name = "user_cell_phone_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private PhoneNumberEntity cellPhone;

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="street_address_id")
	@ForeignKey(name="user_street_address_fk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private StreetAddressEntity streetAddress;
	
	@UserRoles
	@ManyToMany(targetEntity = UserRoleEntity.class)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "id"))
	
	//@Enumerated(EnumType.STRING)
	//@Column(name = "roles")
	//@ElementCollection(targetClass = Role.class)
	//@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	//private List<UserPermission> permissions;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return this.passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public EmailAddressEntity getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(EmailAddressEntity emailAddress) {
		this.emailAddress = emailAddress;
	}

//	public EmailAccountEntity getEmailAccount() {
//		return emailAccount;
//	}
//
//	public void setEmailAccount(EmailAccountEntity emailAccount) {
//		this.emailAccount = emailAccount;
//	}
	
	public PhoneNumberEntity getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(PhoneNumberEntity homePhone) {
		this.homePhone = homePhone;
	}

	public PhoneNumberEntity getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(PhoneNumberEntity cellPhone) {
		this.cellPhone = cellPhone;
	}

	public StreetAddressEntity getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(StreetAddressEntity streetAddress) {
		this.streetAddress = streetAddress;
	}
	
//	public List<UserPermission> getPermissions() {
//		return permissions;
//	}
//
//	public void setRoles(List<UserPermission> permissions) {
//		this.permissions = permissions;
//	}

	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		UserEntity other = (UserEntity) object;
		if (this.getId() == null || other.getId() == null)
			return this == other;
		if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		return 0;
	}
	
	@Override
	public String toString() {
		return userId;
	}

}
