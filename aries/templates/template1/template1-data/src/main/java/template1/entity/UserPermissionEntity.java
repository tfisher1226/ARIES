package template1.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import template1.model.UserRole;


@Entity(name = "UserPermission")
@Table(name = "userPermission")
@SuppressWarnings("serial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserPermissionEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "roles")
	@ElementCollection(targetClass = UserRole.class)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))
	private Set<UserRole> roles;

//	@ManyToMany(targetEntity = UserOperation.class)
//	@JoinTable(name = "user_permission_operations", 
//		joinColumns = @JoinColumn(name = "id"),
//		inverseJoinColumns = @JoinColumn(name = "id"))
//	private Set<UserOperation> operations;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<UserRole> getRoles() { 
		return roles; 
	}
	
	public void setRoles(Set<UserRole> roles) { 
		this.roles = roles; 
	}  

//	public Set<UserOperation> getOperations() { 
//		return operations; 
//	}
//	
//	public void setOperations(Set<UserOperation> operations) { 
//		this.operations = operations; 
//	}  
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		UserPermissionEntity other = (UserPermissionEntity) object;
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

}
