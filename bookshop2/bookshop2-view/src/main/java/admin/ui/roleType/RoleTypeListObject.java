package admin.ui.roleType;

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
	public String getLabel() {
		return toString(roleType);
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
		String thisText = toString(this.roleType);
		String otherText = toString(other.roleType);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
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
