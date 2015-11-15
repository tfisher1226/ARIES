package admin.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;

import admin.RoleType;


@Entity(name = "Role")
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Cache(usage = READ_WRITE)
public class RoleEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//SEAM @RoleName
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "role_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	//SEAM @RoleGroups
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "role_group", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	@ForeignKey(name = "role_group_fk", inverseName = "role_group_inverse_fk")
	@Cache(usage = READ_WRITE)
	private Set<RoleEntity> groups;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	@ForeignKey(name = "role_permission_fk", inverseName = "role_permission_inverse_fk")
	@Cache(usage = READ_WRITE)
	private List<PermissionEntity> permissions;
	
	//SEAM @RoleConditional
	@Column(name = "conditional")
	private Boolean conditional;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	
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
	
	public Set<RoleEntity> getGroups() {
		if (groups == null)
			groups = new HashSet<RoleEntity>();
		return groups;
	}
	
	public void setGroups(Set<RoleEntity> groups) {
		this.groups = new HashSet<RoleEntity>();
		this.groups.addAll(groups);
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
	
	public Boolean isConditional() {
		return conditional;
	}
	
	public Boolean getConditional() {
		return conditional;
	}
	
	public void setConditional(Boolean conditional) {
		this.conditional = conditional;
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
			return getClass().getSimpleName()+"["+getId()+"] name="+getName();
		return "getClass().getSimpleName(): name="+getName();
	}
	
}
