package admin.role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Role;
import admin.util.RoleUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("roleDataManager")
public class RoleDataManager implements Serializable {
	
	@Inject
	private RoleEventManager roleEventManager;
	
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
	
	public Collection<Role> getRoleList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Role> getDefaultList() {
		return null;
	}
	
	public void saveRole(Role role) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeRole(Role role) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
