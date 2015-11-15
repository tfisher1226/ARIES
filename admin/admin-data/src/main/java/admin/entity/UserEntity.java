package admin.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.aries.common.entity.EmailAddressEntity;
import org.aries.common.entity.PersonNameEntity;
import org.aries.common.entity.PhoneNumberEntity;
import org.aries.common.entity.StreetAddressEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;


@Entity(name = "User")
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
@Cache(usage = READ_WRITE)
public class UserEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "name_id", nullable = false)
//	@ForeignKey(name = "user_name_fk", inverseName = "user_name_inverse_fk")
//	@Cache(usage = READ_WRITE)
//	private PersonNameEntity name;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "person_name_id", nullable = false)
	@ForeignKey(name = "user_person_name_fk", inverseName = "user_person_name_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PersonNameEntity personName;
	
	//SEAM @UserPrincipal
	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;
	
	//SEAM @UserPassword(hash = "MD5")
	@Column(name = "password_hash", nullable = false)
	private String password;
	
	//SEAM @PasswordSalt
	@Column(name = "password_salt")
	private String password2;
	
	//SEAM @UserEnabled
	@Column(name = "enabled")
	private Boolean enabled;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "email_address_id", nullable = false)
	@ForeignKey(name = "user_email_address_fk", inverseName = "user_email_address_inverse_fk")
	@Cache(usage = READ_WRITE)
	private EmailAddressEntity emailAddress;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "street_address_id")
	@ForeignKey(name = "user_street_address_fk", inverseName = "user_street_address_inverse_fk")
	@Cache(usage = READ_WRITE)
	private StreetAddressEntity streetAddress;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "home_phone_id")
	@ForeignKey(name = "user_home_phone_fk", inverseName = "user_home_phone_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PhoneNumberEntity homePhone;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cell_phone_id")
	@ForeignKey(name = "user_cell_phone_fk", inverseName = "user_cell_phone_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PhoneNumberEntity cellPhone;
	
	//SEAM @UserRoles
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@ForeignKey(name = "user_role_fk", inverseName = "user_role_inverse_fk")
	@Cache(usage = READ_WRITE)
	private Set<RoleEntity> roles;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	@ForeignKey(name = "user_permission_fk", inverseName = "user_permission_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<PermissionEntity> permissions;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "preferences_id")
	@ForeignKey(name = "user_preferences_fk", inverseName = "user_preferences_inverse_fk")
	@Cache(usage = READ_WRITE)
	private PreferencesEntity preferences;
	
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "last_update")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
//	public PersonNameEntity getName() {
//		return name;
//	}
//	
//	public void setName(PersonNameEntity name) {
//		this.name = name;
//	}
	
	public PersonNameEntity getPersonName() {
		return personName;
	}
	
	public void setPersonName(PersonNameEntity personName) {
		this.personName = personName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public void setPassword2(String password2) {
		this.password2 = password2;
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
	
	public PhoneNumberEntity getHomePhone() {
		return homePhone;
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
	
	public Set<RoleEntity> getRoles() {
		if (roles == null)
			roles = new HashSet<RoleEntity>();
		return roles;
	}
	
	public void setRoles(Set<RoleEntity> roles) {
		this.roles = new HashSet<RoleEntity>();
		this.roles.addAll(roles);
	}

	public List<PermissionEntity> getPermissions() {
		if (permissions == null)
			permissions = new ArrayList<PermissionEntity>();
		return permissions;
	}
	
	public void setPermissions(List<PermissionEntity> permissions) {
		this.permissions = new ArrayList<PermissionEntity>();
		this.permissions.addAll(permissions);
	}
		
	public PreferencesEntity getPreferences() {
		return preferences;
	}
	
	public void setPreferences(PreferencesEntity preferences) {
		this.preferences = preferences;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] userName="+getUserName();
		return "getClass().getSimpleName(): userName="+getUserName();
	}
	
}
