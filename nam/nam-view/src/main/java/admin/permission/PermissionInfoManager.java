package admin.permission;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.util.Validator;

import admin.Permission;
import admin.util.PermissionUtil;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("permissionInfoManager")
public class PermissionInfoManager extends AbstractNamRecordManager<Permission> implements Serializable {
	
	@Inject
	private PermissionWizard permissionWizard;
	
	@Inject
	private PermissionDataManager permissionDataManager;
	
	@Inject
	private PermissionPageManager permissionPageManager;
	
	@Inject
	private PermissionEventManager permissionEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;

	@Inject
	private PermissionHelper permissionHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PermissionInfoManager() {
		setInstanceName("permission");
	}

	
	public Permission getPermission() {
		return getRecord();
	}

	public Permission getSelectedPermission() {
		return selectionContext.getSelection("permission");
	}
	
	@Override
	public Class<Permission> getRecordClass() {
		return Permission.class;
	}
	
	@Override
	public boolean isEmpty(Permission permission) {
		return permissionHelper.isEmpty(permission);
	}
	
	@Override
	public String toString(Permission permission) {
		return permissionHelper.toString(permission);
	}
	
	@Override
	public void initialize() {
		Permission permission = selectionContext.getSelection("permission");
		if (permission != null)
			initialize(permission);
	}
	
	protected void initialize(Permission permission) {
		permissionWizard.initialize(permission);
		setContext("permission", permission);
	}
	
	@Override
	public String newRecord() {
		return newPermission();
	}
	
	public String newPermission() {
		try {
			Permission permission = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("permission",  permission);
			String url = permissionPageManager.initializePermissionCreationPage(permission);
			permissionPageManager.pushContext(permissionWizard);
			initialize(permission);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Permission create() {
		Permission permission = PermissionUtil.create();
		return permission;
	}
	
	@Override
	public Permission clone(Permission permission) {
		permission = PermissionUtil.clone(permission);
		return permission;
	}
	
	@Override
	public String viewRecord() {
		return viewPermission();
	}
	
	public String viewPermission() {
		Permission permission = selectionContext.getSelection("permission");
		String url = viewPermission(permission);
		return url;
	}
	
	public String viewPermission(Permission permission) {
		try {
			String url = permissionPageManager.initializePermissionSummaryView(permission);
			permissionPageManager.pushContext(permissionWizard);
			initialize(permission);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editPermission();
	}
	
	public String editPermission() {
		Permission permission = selectionContext.getSelection("permission");
		String url = editPermission(permission);
		return url;
	}
	
	public String editPermission(Permission permission) {
		try {
			//permission = clone(permission);
			selectionContext.resetOrigin();
			selectionContext.setSelection("permission",  permission);
			String url = permissionPageManager.initializePermissionUpdatePage(permission);
			permissionPageManager.pushContext(permissionWizard);
			initialize(permission);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public void savePermission() {
		Permission permission = getPermission();
		if (validatePermission(permission)) {
			savePermission(permission);
		}
	}

	public void persistPermission(Permission permission) {
		Long id = permission.getId();
		if (id != null) {
			savePermission(permission);
		}
	}
	
	public void savePermission(Permission permission) {
		try {
			savePermissionToSystem(permission);
			permissionEventManager.fireAddedEvent(permission);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void savePermissionToSystem(Permission permission) {
		permissionDataManager.savePermission(permission);
	}
	
	public void handleSavePermission(@Observes @Add Permission permission) {
		savePermission(permission);
	}
	
	public void enrichPermission(Permission permission) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Permission permission) {
		return validatePermission(permission);
	}
	
	public boolean validatePermission(Permission permission) {
		Validator validator = getValidator();
		boolean isValid = PermissionUtil.validate(permission);
		Display display = getFromSession("display");
		display.setModule("permissionInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}

	public void promptRemovePermission() {
		display = getFromSession("display");
		display.setModule("permissionInfo");
		Permission permission = selectionContext.getSelection("permission");
		if (permission == null) {
			display.error("Permission record must be selected.");
		}
	}
	
	public String handleRemovePermission(@Observes @Remove Permission permission) {
		display = getFromSession("display");
		display.setModule("permissionInfo");
		try {
			display.info("Removing Permission "+PermissionUtil.getLabel(permission)+" from the system.");
			removePermissionFromSystem(permission);
			selectionContext.clearSelection("permission");
			permissionEventManager.fireClearSelectionEvent();
			permissionEventManager.fireRemovedEvent(permission);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removePermissionFromSystem(Permission permission) {
		if (permissionDataManager.removePermission(permission))
			setRecord(null);
	}

	public void cancelPermission() {
		BeanContext.removeFromSession("permission");
		permissionPageManager.removeContext(permissionWizard);
	}
	
}
