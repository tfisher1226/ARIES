package admin.permission;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Permission;
import admin.util.PermissionUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("permissionDataManager")
public class PermissionDataManager implements Serializable {
	
	@Inject
	private PermissionEventManager permissionEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Permission> getPermissionList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Permission> getDefaultList() {
		return null;
	}
	
	public void savePermission(Permission permission) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removePermission(Permission permission) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
