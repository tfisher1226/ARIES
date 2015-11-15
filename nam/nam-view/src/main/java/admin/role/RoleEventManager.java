package admin.role;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Role;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("roleEventManager")
public class RoleEventManager extends AbstractEventManager<Role> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Role getInstance() {
		return selectionContext.getSelection("role");
	}
	
	public void removeRole() {
		Role role = getInstance();
		fireRemoveEvent(role);
	}
	
}
