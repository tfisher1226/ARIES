package admin.ui.permission;

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
	public String getLabel() {
		return toString(permission);
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
		String thisText = toString(this.permission);
		String otherText = toString(other.permission);
		if (thisText == null)
			return -1;
		if (otherText == null)
			return 1;
		return thisText.compareTo(otherText);
	}
	
	@Override
	public boolean equals(Object object) {
		PermissionListObject other = (PermissionListObject) object;
		Long thisId = this.permission.getId();
		Long otherId = other.permission.getId();
		if (thisId == null)
			return false;
		if (otherId == null)
			return false;
		return thisId.equals(otherId);
	}

}
