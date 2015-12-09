package admin.roleType;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.RoleType;


public class RoleTypeListObject extends AbstractListObject<RoleType> implements Comparable<RoleTypeListObject>, Serializable {
	
	private RoleType roleType;
	
	
	public RoleTypeListObject(RoleType roleType) {
		this.roleType = roleType;
	}
	
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	@Override
	public Object getKey() {
		return roleType.name();
	}
	
	@Override
	public String getLabel() {
		return roleType.name();
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
	}
	
	@Override
	public String getIcon() {
		return "/icons/nam/RoleType16.gif";
	}
	
	@Override
	public String toString() {
		return toString(roleType);
	}
	
	@Override
	public String toString(RoleType roleType) {
		return roleType.name();
	}
	
	@Override
	public int compareTo(RoleTypeListObject other) {
		String thisText = this.roleType.name();
		String otherText = other.roleType.name();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		RoleTypeListObject other = (RoleTypeListObject) object;
		String thisText = toString(this.roleType);
		String otherText = toString(other.roleType);
		return thisText.equals(otherText);
	}
	
}
