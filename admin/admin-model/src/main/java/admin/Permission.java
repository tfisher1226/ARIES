package admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.aries.adapter.BooleanAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Permission", namespace = "http://admin", propOrder = {
	"id",
	"target",
	"organization",
	"actions",
	"enabled"
})
@XmlRootElement(name = "permission", namespace = "http://admin")
public class Permission implements Comparable<Object>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://admin")
	private Long id;
	
	@XmlElement(name = "target", namespace = "http://admin")
	private String target;
	
	@XmlElement(name = "organization", namespace = "http://admin")
	private String organization;
	
	@XmlElement(name = "actions", namespace = "http://admin", required = true)
	private List<Action> actions;
	
	@XmlElement(name = "enabled", namespace = "http://admin", type = String.class, defaultValue = "true")
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	@XmlSchemaType(name = "boolean")
	private Boolean enabled = true;
	
	
	public Permission() {
		actions = new ArrayList<Action>();
	}
	
	
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
		synchronized (actions) {
			return actions;
		}
	}
	
	public void setActions(Collection<Action> actions) {
		if (actions == null) {
			this.actions = null;
		} else {
		synchronized (this.actions) {
			this.actions = new ArrayList<Action>();
			addToActions(actions);
		}
	}
	}

	public void addToActions(Action actions) {
		if (actions != null ) {
			synchronized (this.actions) {
				this.actions.add(actions);
			}
		}
	}

	public void addToActions(Collection<Action> actionsCollection) {
		if (actionsCollection != null && !actionsCollection.isEmpty()) {
			synchronized (this.actions) {
				this.actions.addAll(actionsCollection);
			}
		}
	}

	public void removeFromActions(Action actions) {
		if (actions != null ) {
			synchronized (this.actions) {
				this.actions.remove(actions);
			}
		}
	}

	public void removeFromActions(Collection<Action> actionsCollection) {
		if (actionsCollection != null ) {
			synchronized (this.actions) {
				this.actions.removeAll(actionsCollection);
			}
		}
	}

	public void clearActions() {
		synchronized (actions) {
			actions.clear();
		}
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
			Permission other = (Permission) object;
		int status = compare(actions, other.actions);
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
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Permission other = (Permission) object;
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
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Permission: actions="+actions;
	}
	
}
