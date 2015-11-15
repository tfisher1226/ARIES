package admin.ui.role;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Role;
import admin.util.RoleUtil;


public class RoleListObject extends AbstractListObject<Role> implements Comparable<RoleListObject>, Serializable {
	
	private Role role;
	
	
	public RoleListObject(Role role) {
		this.role = role;
	}
	
	
	public Role getRole() {
		return role;
	}
	
	@Override
	public String getLabel() {
		return toString(role);
	}
	
	@Override
	public String toString() {
		return toString(role);
	}
	
	@Override
	public String toString(Role role) {
		return RoleUtil.toString(role);
	}
	
	@Override
	public int compareTo(RoleListObject other) {
		String thisText = toString(this.role);
		String otherText = toString(other.role);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		RoleListObject other = (RoleListObject) object;
		Long thisId = this.role.getId();
		Long otherId = other.role.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}
	
}
