package admin.role;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;

import admin.Role;
import admin.client.roleService.RoleService;
import admin.util.RoleUtil;


@SessionScoped
@Named("roleInfoManager")
public class RoleInfoManager extends AbstractNamRecordManager<Role> implements Serializable {

	@Inject
	private RoleWizard roleWizard;
	
	@Inject
	private RoleDataManager roleDataManager;
	
	@Inject
	private RolePageManager rolePageManager;
	
	@Inject
	private RoleEventManager roleEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private RoleHelper roleHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public RoleInfoManager() {
		setInstanceName("role");
	}

	
	public Role getRole() {
		return getRecord();
	}
	
	public Role getSelectedRole() {
		return selectionContext.getSelection("role");
	}
	
	@Override
	public Class<Role> getRecordClass() {
		return Role.class;
	}
	
	@Override
	public boolean isEmpty(Role role) {
		return roleHelper.isEmpty(role);
	}
	
	@Override
	public String toString(Role role) {
		return roleHelper.toString(role);
	}
	
	protected RoleService getRoleService() {
		return BeanContext.getFromSession(RoleService.ID);
	}

	@Override
	public void initialize() {
		Role role = selectionContext.getSelection("role");
		if (role != null)
			initialize(role);
	}
	
	protected void initialize(Role role) {
		RoleUtil.initialize(role);
		roleWizard.initialize(role);
		setContext("role", role);
	}
	
	public void handleRoleSelected(@Observes @Selected Role role) {
		selectionContext.setSelection("role",  role);
		rolePageManager.updateState(role);
		rolePageManager.refreshMembers();
		setRecord(role);
	}
	
	@Override
	public String newRecord() {
		return newRole();
	}
	
	public String newRole() {
		try {
			Role role = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("role",  role);
			String url = rolePageManager.initializeRoleCreationPage(role);
			rolePageManager.pushContext(roleWizard);
			initialize(role);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Role create() {
		Role role = RoleUtil.create();
		return role;
	}
	
	@Override
	public Role clone(Role role) {
		role = RoleUtil.clone(role);
		return role;
	}
	
	@Override
	public String viewRecord() {
		return viewRole();
	}
	
	public String viewRole() {
		Role role = selectionContext.getSelection("role");
		String url = viewRole(role);
		return url;
	}
	
	public String viewRole(Role role) {
		try {
			String url = rolePageManager.initializeRoleSummaryView(role);
			rolePageManager.pushContext(roleWizard);
			initialize(role);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editRole();
	}
	
	public String editRole() {
		Role role = selectionContext.getSelection("role");
		String url = editRole(role);
		return url;
	}
	
	public String editRole(Role role) {
		try {
			//role = clone(role);
			selectionContext.resetOrigin();
			selectionContext.setSelection("role",  role);
			String url = rolePageManager.initializeRoleUpdatePage(role);
			rolePageManager.pushContext(roleWizard);
			initialize(role);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
		
	public void saveRole() {
		Role role = getRole();
		if (validateRole(role)) {
			if (isImmediate())
				persistRole(role);
			outject("role", role);
		}
	}
	
	public void persistRole(Role role) {
		Long id = role.getId();
		if (id != null) {
			saveRole(role);
		} else {
			addRole(role);
		}
	}
	
	public void saveRole(Role role) {
		try {
			getRoleService().saveRole(role);
		} catch (Exception e) {
			handleException(e);
		}
	}

	protected void saveRoleToSystem(Role role) {
		roleDataManager.saveRole(role);
	}
	
	public void handleSaveRole(@Observes @Add Role role) {
		saveRole(role);
	}
	
	public void addRole(Role role) {
		try {
			Long id = getRoleService().addRole(role);
			Assert.notNull(id, "Role Id should not be null");
			raiseEvent("org.aries.refreshRoleList");
			raiseEvent(actionEvent);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichRole(Role role) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Role role) {
		return validateRole(role);
	}
	
	public boolean validateRole(Role role) {
		Validator validator = getValidator();
		boolean isValid = RoleUtil.validate(role);
		Display display = getFromSession("display");
		display.setModule("roleInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveRole() {
		display = getFromSession("display");
		display.setModule("roleInfo");
		Role role = selectionContext.getSelection("role");
		if (role == null) {
			display.error("Role record must be selected.");
		}
	}
	
	public String handleRemoveRole(@Observes @Remove Role role) {
		display = getFromSession("display");
		display.setModule("roleInfo");
		try {
			display.info("Removing Role "+RoleUtil.getLabel(role)+" from the system.");
			removeRoleFromSystem(role);
			selectionContext.clearSelection("role");
			roleEventManager.fireClearSelectionEvent();
			roleEventManager.fireRemovedEvent(role);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeRoleFromSystem(Role role) {
		if (roleDataManager.removeRole(role))
			setRecord(null);
	}
	
	public void cancelRole() {
		BeanContext.removeFromSession("role");
		rolePageManager.removeContext(roleWizard);
	}
	
}
