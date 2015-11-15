package template1.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jboss.seam.annotations.security.management.RoleConditional;
import org.jboss.seam.annotations.security.management.RoleName;


@Entity(name = "Role")
@Table(name = "role")
@SuppressWarnings("serial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRoleEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@RoleName
	@Column(name = "role_name", nullable=false, unique=true)
	private String roleName;

	@RoleConditional
	private boolean conditional;

//	@RoleGroups
//	@ManyToMany(targetEntity = Operation.class)
//	@JoinTable(name = "RoleGroups", 
//		joinColumns = @JoinColumn(name = "id"),
//		inverseJoinColumns = @JoinColumn(name = "id"))
//	private Set<Operation> groups;

	  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isConditional() { 
		return conditional; 
	}
	
	public void setConditional(boolean conditional) { 
		this.conditional = conditional; 
	}

//	public Set<Operation> getGroups() { 
//		return groups; 
//	}
//	
//	public void setGroups(Set<Operation> groups) { 
//		this.groups = groups; 
//	}  

	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		UserRoleEntity other = (UserRoleEntity) object;
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
		return roleName;
	}

}
