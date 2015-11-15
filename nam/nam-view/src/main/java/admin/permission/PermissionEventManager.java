package admin.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Permission;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("permissionEventManager")
public class PermissionEventManager extends AbstractEventManager<Permission> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Permission getInstance() {
		return selectionContext.getSelection("permission");
	}
	
	public void removePermission() {
		Permission permission = getInstance();
		fireRemoveEvent(permission);
	}
	
}
