package admin.entity;

import static javax.persistence.FetchType.EAGER;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;

import admin.Action;


@Entity(name = "Permission")
@Table(name = "permission")
@Cache(usage = READ_WRITE)
public class PermissionEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "target")
	private String target;
	
	@Column(name = "organization")
	private String organization;
	
	@Column(name = "actions", nullable = false)
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Action.class, fetch = EAGER)
	@CollectionTable(name = "permission_action", joinColumns = @JoinColumn(name = "permission_id"))
	@Cache(usage = READ_WRITE)
	private List<Action> actions;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getOrganization() {
		return organization;
	}
	
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public List<Action> getActions() {
		if (actions == null)
			actions = new ArrayList<Action>();
		return actions;
	}
	
	public void setActions(List<Action> actions) {
		this.actions = new ArrayList<Action>();
		this.actions.addAll(actions);
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
			return getClass().getSimpleName()+"["+getId()+"]";
		return super.toString();
	}
	
}
