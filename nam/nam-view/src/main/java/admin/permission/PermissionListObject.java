package admin.permission;

import java.io.Serializable;

import org.aries.ui.AbstractListObject;

import admin.Permission;
import admin.util.PermissionUtil;


public class PermissionListObject extends AbstractListObject<Permission> implements Comparable<PermissionListObject>, Serializable {
	
	private Permission permission;

	
	public PermissionListObject(Permission permission) {
		this.permission = permission;
	}

	
	public Permission getPermission() {
		return permission;
	}

	@Override
	public Object getKey() {
		return getKey(permission);
	}
	
	public Object getKey(Permission permission) {
		return PermissionUtil.getKey(permission);
	}
	
	@Override
	public String getLabel() {
		return getLabel(permission);
	}
	
	public String getLabel(Permission permission) {
		return PermissionUtil.getLabel(permission);
	}
	
	@Override
	public String toString() {
		return toString(permission);
	}
	
	@Override
	public String toString(Permission permission) {
		return PermissionUtil.toString(permission);
	}
	
	@Override
	public int compareTo(PermissionListObject other) {
		Object thisKey = getKey(this.permission);
		Object otherKey = getKey(other.permission);
		String thisText = thisKey.toString();
		String otherText = otherKey.toString();
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PermissionListObject other = (PermissionListObject) object;
		Object thisKey = PermissionUtil.getKey(this.permission);
		Object otherKey = PermissionUtil.getKey(other.permission);
		if (thisKey == null)
			return false;
		if (otherKey == null)
			return false;
		return thisKey.equals(otherKey);
	}

}
