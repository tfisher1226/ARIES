package admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;
import org.aries.adapter.DateTimeAdapter;
import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.PhoneNumber;
import org.aries.common.StreetAddress;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", namespace = "http://admin", propOrder = {
	"id",
	"personName",
	"userName",
	"password",
	"password2",
	"enabled",
	"emailAddress",
	"streetAddress",
	"homePhone",
	"cellPhone",
	"roles",
	"permissions",
	"preferences",
	"creationDate",
	"lastUpdate"
})
@XmlRootElement(name = "user", namespace = "http://admin")
public class User implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "personName", namespace = "http://admin", required = true)
	private PersonName personName;
	
	@XmlElement(name = "userName", namespace = "http://admin", required = true)
	private String userName;
	
	@XmlElement(name = "password", namespace = "http://admin", required = true)
	private String password;
	
	@XmlElement(name = "password2", namespace = "http://admin")
	private String password2;
	
	@XmlElement(name = "enabled", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled = true;
	
	@XmlElement(name = "emailAddress", namespace = "http://admin", required = true)
	private EmailAddress emailAddress;
	
	@XmlElement(name = "streetAddress", namespace = "http://admin")
	private StreetAddress streetAddress;
	
	@XmlElement(name = "homePhone", namespace = "http://admin")
	private PhoneNumber homePhone;
	
	@XmlElement(name = "cellPhone", namespace = "http://admin")
	private PhoneNumber cellPhone;
	
	@XmlElement(name = "roles", namespace = "http://admin", required = true)
	private Set<Role> roles;
	
	@XmlElement(name = "permissions", namespace = "http://admin", nillable = true)
	private List<Permission> permissions;
	
	@XmlElement(name = "preferences", namespace = "http://admin", nillable = true)
	private Preferences preferences;
	
	@XmlElement(name = "creationDate", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date creationDate;
	
	@XmlElement(name = "lastUpdate", namespace = "http://admin", type = String.class)
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	@XmlSchemaType(name = "dateTime")
	private Date lastUpdate;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public User() {
		roles = new HashSet<Role>();
		permissions = new ArrayList<Permission>();
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public PersonName getPersonName() {
		return personName;
	}
	
	public void setPersonName(PersonName personName) {
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
		return enabled != null && enabled;
	}
	
	public Boolean getEnabled() {
		return enabled != null && enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public StreetAddress getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(StreetAddress streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public PhoneNumber getHomePhone() {
		return homePhone;
	}
	
	public void setHomePhone(PhoneNumber homePhone) {
		this.homePhone = homePhone;
	}
	
	public PhoneNumber getCellPhone() {
		return cellPhone;
	}
	
	public void setCellPhone(PhoneNumber cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public Set<Role> getRoles() {
		synchronized (roles) {
			return roles;
		}
	}
	
	public void setRoles(Collection<Role> roles) {
		if (roles == null) {
			this.roles = null;
		} else {
		synchronized (this.roles) {
			this.roles = new HashSet<Role>();
			addToRoles(roles);
		}
	}
	}

	public void addToRoles(Role role) {
		if (role != null ) {
			synchronized (this.roles) {
				this.roles.add(role);
			}
		}
	}

	public void addToRoles(Collection<Role> roleCollection) {
		if (roleCollection != null && !roleCollection.isEmpty()) {
			synchronized (this.roles) {
				this.roles.addAll(roleCollection);
			}
		}
	}

	public void removeFromRoles(Role role) {
		if (role != null ) {
			synchronized (this.roles) {
				this.roles.remove(role);
			}
		}
	}

	public void removeFromRoles(Collection<Role> roleCollection) {
		if (roleCollection != null ) {
			synchronized (this.roles) {
				this.roles.removeAll(roleCollection);
			}
		}
	}

	public void clearRoles() {
		synchronized (roles) {
			roles.clear();
		}
	}
	
	public List<Permission> getPermissions() {
		synchronized (permissions) {
			return permissions;
		}
	}
	
	public void setPermissions(Collection<Permission> permissions) {
		if (permissions == null) {
			this.permissions = null;
		} else {
		synchronized (this.permissions) {
			this.permissions = new ArrayList<Permission>();
			addToPermissions(permissions);
		}
	}
	}

	public void addToPermissions(Permission permission) {
		if (permission != null ) {
			synchronized (this.permissions) {
				this.permissions.add(permission);
			}
		}
	}

	public void addToPermissions(Collection<Permission> permissionCollection) {
		if (permissionCollection != null && !permissionCollection.isEmpty()) {
			synchronized (this.permissions) {
				this.permissions.addAll(permissionCollection);
			}
		}
	}

	public void removeFromPermissions(Permission permission) {
		if (permission != null ) {
			synchronized (this.permissions) {
				this.permissions.remove(permission);
			}
		}
	}

	public void removeFromPermissions(Collection<Permission> permissionCollection) {
		if (permissionCollection != null ) {
			synchronized (this.permissions) {
				this.permissions.removeAll(permissionCollection);
			}
		}
	}

	public void clearPermissions() {
		synchronized (permissions) {
			permissions.clear();
		}
	}
	
	public Preferences getPreferences() {
		return preferences;
	}
	
	public void setPreferences(Preferences preferences) {
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
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	protected <T extends Comparable<T>> int compare(Collection<T> collecton1, Collection<T> collecton2) {
		if (collecton1 == null && collecton2 == null) return 0;
		if (collecton1 != null && collecton2 == null) return 1;
		if (collecton1 == null && collecton2 != null) return -1;
		int status = compare(collecton1.size(), collecton2.size());
		if (status != 0)
			return status;
		Iterator<T> iterator1 = collecton1.iterator();
		Iterator<T> iterator2 = collecton2.iterator();
		while (iterator2.hasNext() && iterator2.hasNext()) {
			T value1 = iterator1.next();
			T value2 = iterator2.next();
			status = value1.compareTo(value2);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	@Override
	public int compareTo(Object object) {
		if (object.getClass().isAssignableFrom(this.getClass())) {
			User other = (User) object;
			int status = compare(personName, other.personName);
			if (status != 0)
				return status;
			status = compare(userName, other.userName);
			if (status != 0)
				return status;
			status = compare(password, other.password);
			if (status != 0)
				return status;
			status = compare(emailAddress, other.emailAddress);
			if (status != 0)
				return status;
		}
		return 0;
	}
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	protected <T extends Comparable<Object>> int compareObject(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		User other = (User) object;
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
		if (userName != null)
			hashCode += userName.hashCode();
		if (hashCode == 0)
		return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "User: userName="+userName;
	}
	
}
