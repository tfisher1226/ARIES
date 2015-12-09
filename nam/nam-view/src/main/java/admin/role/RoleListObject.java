package admin.role;

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
	public Object getKey() {
		return getKey(role);
	}
	
	public Object getKey(Role role) {
		return RoleUtil.getKey(role);
	}
	
	@Override
	public String getLabel() {
		return getLabel(role);
	}
	
	public String getLabel(Role role) {
		return RoleUtil.getLabel(role);
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/Role16.gif";
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
		Object thisKey = getKey(this.role);
		Object otherKey = getKey(other.role);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		RoleListObject other = (RoleListObject) object;
		Object thisKey = RoleUtil.getKey(this.role);
		Object otherKey = RoleUtil.getKey(other.role);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}
	
}
