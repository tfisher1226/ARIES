package admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Role", namespace = "http://admin", propOrder = {
	"id",
	"name",
	"roleType",
	"groups",
	"permissions",
	"conditional",
	"enabled"
})
@XmlRootElement(name = "role", namespace = "http://admin")
public class Role implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "name", namespace = "http://admin", required = true)
	private String name;
	
	@XmlElement(name = "roleType", namespace = "http://admin", defaultValue = "RoleType.USER", required = true)
	private RoleType roleType = RoleType.USER;
	
	@XmlElement(name = "groups", namespace = "http://admin")
	private Set<Role> groups;
	
	@XmlElement(name = "permissions", namespace = "http://admin", required = true)
	private List<Permission> permissions;
	
	@XmlElement(name = "conditional", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean conditional = true;
	
	@XmlElement(name = "enabled", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled = true;
	
	@XmlAttribute(name = "ref")
	private String ref;
	
	
	public Role() {
		groups = new HashSet<Role>();
		permissions = new ArrayList<Permission>();
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	public Set<Role> getGroups() {
		synchronized (groups) {
			return groups;
		}
	}
	
	public void setGroups(Collection<Role> groups) {
		if (groups == null) {
			this.groups = null;
		} else {
		synchronized (this.groups) {
			this.groups = new HashSet<Role>();
			addToGroups(groups);
	}
	}
	}

	public void addToGroups(Role role) {
		if (role != null ) {
			synchronized (this.groups) {
				this.groups.add(role);
			}
		}
	}

	public void addToGroups(Collection<Role> roleCollection) {
		if (roleCollection != null && !roleCollection.isEmpty()) {
			synchronized (this.groups) {
				this.groups.addAll(roleCollection);
			}
		}
	}

	public void removeFromGroups(Role role) {
		if (role != null ) {
			synchronized (this.groups) {
				this.groups.remove(role);
			}
		}
	}

	public void removeFromGroups(Collection<Role> roleCollection) {
		if (roleCollection != null ) {
			synchronized (this.groups) {
				this.groups.removeAll(roleCollection);
			}
		}
	}

	public void clearGroups() {
		synchronized (groups) {
			groups.clear();
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
	
	public Boolean isConditional() {
		return conditional != null && conditional;
	}
	
	public Boolean getConditional() {
		return conditional != null && conditional;
	}
	
	public void setConditional(Boolean conditional) {
		this.conditional = conditional;
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
			Role other = (Role) object;
		int status = compare(name, other.name);
		if (status != 0)
			return status;
		status = compare(roleType, other.roleType);
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
		Role other = (Role) object;
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
		if (name != null)
			hashCode += name.hashCode();
		if (hashCode == 0)
		return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Role: name="+name+", roleType="+roleType+", permissions="+permissions;
	}
	
}
