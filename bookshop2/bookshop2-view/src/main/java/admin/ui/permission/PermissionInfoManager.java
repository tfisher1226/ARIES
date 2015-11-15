package admin.ui.permission;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.util.Validator;

import admin.Permission;
import admin.ui.action.ActionListManager;
import admin.ui.action.ActionSelectManager;
import admin.util.PermissionUtil;


@SessionScoped
@Named("permissionInfoManager")
public class PermissionInfoManager extends AbstractRecordManager<Permission> implements Serializable {
	
	private ActionSelectManager actionSelectManager;

	private ActionListManager actionListManager;
	
	
	public PermissionInfoManager() {
		setInstanceName("permission");
	}


	public Permission getPermission() {
		return getRecord();
	}

	@Override
	public Class<Permission> getRecordClass() {
		return Permission.class;
	}
	
	@Override
	public boolean isEmpty(Permission permission) {
		return getPermissionHelper().isEmpty(permission);
	}
	
	@Override
	public String toString(Permission permission) {
		return getPermissionHelper().toString(permission);
	}
	
	protected PermissionHelper getPermissionHelper() {
		return BeanContext.getFromSession("permissionHelper");
	}
	
	protected void initialize(Permission permission) {
		PermissionUtil.initialize(permission);
		initializeActionListManager(permission);
		initializeActionSelectManager(permission);
		initializeOutjectedState(permission);
		setContext("permission", permission);
	}
	
	protected void initializeOutjectedState(Permission permission) {
		outjectTo("permissionActions", permission.getActions());
		outject(instanceName, permission);
	}
	
	protected void initializeActionListManager(Permission permission) {
		actionListManager = BeanContext.getFromSession("actionListManager");
		actionListManager.setContext("Permission", toString(permission));
		actionListManager.initialize();
	}

	protected void initializeActionSelectManager(Permission permission) {
		actionSelectManager = BeanContext.getFromSession("actionSelectManager");
		actionSelectManager.setContext("Permission", toString(permission));
		actionSelectManager.setOwnerRecord(permission);
		actionSelectManager.initialize();
	}
	
	//SEAM @Observer("permissionActionsSelected")
	public void handlePermissionActionsSelected() {
		Permission permission = getPermission();
		permission.getActions().clear();
		permission.getActions().addAll(actionSelectManager.getSelectedRecordList());
	}

	public void activate() {
		initializeContext();
		Permission permission = BeanContext.getFromSession(getInstanceId());
		if (permission == null)
			newPermission();
		else editPermission(permission);
	}
	
	//SEAM @Begin(join = true)
	public void newPermission() {
		try {
			Permission permission = create();
			initialize(permission);
			show();
		} catch (Exception e) {
			handleException(e);
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
	
	//SEAM @Begin(join = true)
	public void editPermission(Permission permission) {
		try {
			permission = clone(permission);
			initialize(permission);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	//SEAM @Observer("org.aries.savePermission")
	public void savePermission() {
		setModule("Permission");
		Permission permission = getPermission();
		enrichPermission(permission);
		if (validate(permission)) {
			savePermission(permission);
		}
	}

	public void processPermission(Permission permission) {
		Long id = permission.getId();
		if (id != null) {
			savePermission(permission);
		}
	}
	
	public void savePermission(Permission permission) {
		try {
			raiseEvent("org.aries.refreshPermissionList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichPermission(Permission permission) {
		//nothing for now
	}
	
	public boolean validate(Permission permission) {
		Validator validator = getValidator();
		boolean isValid = PermissionUtil.validate(permission);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}

}
