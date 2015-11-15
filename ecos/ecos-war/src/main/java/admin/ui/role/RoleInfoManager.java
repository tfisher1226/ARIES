package admin.ui.role;

import java.io.Serializable;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.util.CollectionUtil;
import org.aries.util.Validator;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Permission;
import admin.Role;
import admin.client.roleService.RoleService;
import admin.ui.permission.PermissionInfoManager;
import admin.ui.permission.PermissionListManager;
import admin.ui.roleType.RoleTypeSelectManager;
import admin.util.RoleUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Scope(ScopeType.SESSION)
@Name("roleInfoManager")
public class RoleInfoManager extends AbstractRecordManager<Role> implements Serializable {

	private PermissionInfoManager permissionInfoManager;

	private PermissionListManager permissionListManager;
	
	private RoleSelectManager roleSelectManager;
	
	private RoleListManager roleListManager;
	
	private RoleTypeSelectManager roleTypeSelectManager;
	
	
	public RoleInfoManager() {
		setInstanceName("role");
	}

	
	public Role getRole() {
		return getRecord();
	}
	
	@Override
	public Class<Role> getRecordClass() {
		return Role.class;
	}
	
	@Override
	public boolean isEmpty(Role role) {
		return getRoleHelper().isEmpty(role);
	}
	
	@Override
	public String toString(Role role) {
		return getRoleHelper().toString(role);
	}
	
	protected RoleHelper getRoleHelper() {
		return BeanContext.getFromSession("roleHelper");
	}
	
	protected RoleService getRoleService() {
		return BeanContext.getFromSession(RoleService.ID);
	}

	protected void initialize(Role role) {
		RoleUtil.initialize(role);
		initializePermissionInfoManager(role);
		initializePermissionListManager(role);
		initializeRoleListManager(role);
		initializeRoleSelectManager(role);
		initializeRoleTypeSelectManager(role);
		initializeOutjectedState(role);
		setContext("Role", role);
	}
	
	protected void initializeOutjectedState(Role role) {
		outjectTo("roleGroups", role.getGroups());
		outjectTo("rolePermissions", role.getPermissions());
		outjectTo("roleRoleType", role.getRoleType());
		outject(instanceName, role);
	}
	
	protected void initializePermissionInfoManager(Role role) {
		permissionInfoManager = BeanContext.getFromSession("permissionInfoManager");
		permissionInfoManager.setContext("Role", toString(role));
		permissionInfoManager.initialize();
	}
	
	protected void initializePermissionListManager(Role role) {
		permissionListManager = BeanContext.getFromSession("permissionListManager");
		permissionListManager.setContext("Role", toString(role));
		permissionListManager.initialize();
	}
	
	protected void initializeRoleListManager(Role role) {
		roleListManager = BeanContext.getFromSession("roleListManager");
		roleListManager.setContext("Role", toString(role));
		roleListManager.initialize();
	}
	
	protected void initializeRoleSelectManager(Role role) {
		roleSelectManager = BeanContext.getFromSession("roleSelectManager");
		roleSelectManager.setContext("Role", toString(role));
		roleSelectManager.setOwnerRecord(role);
		roleSelectManager.initialize();
	}
	
	protected void initializeRoleTypeSelectManager(Role role) {
		roleTypeSelectManager = BeanContext.getFromSession("roleTypeSelectManager");
		roleTypeSelectManager.setContext("Role", toString(role));
		roleTypeSelectManager.setOwnerRecord(role);
		roleTypeSelectManager.initialize();
	}
	
	@Observer("roleGroupsSelected")
	public void handleRoleGroupsSelected() {
		Role role = getRole();
		role.getGroups().clear();
		role.getGroups().addAll(roleSelectManager.getSelectedRecordList());
	}
	
	@Observer("rolePermissionsEntered")
	public void handleRolePermissionsEntered() {
		Role role = getRole();
		Permission permission = permissionInfoManager.getPermission();
		CollectionUtil.add(role.getPermissions(), permission);
		permissionListManager.initialize(role.getPermissions());
	}
	
	@Observer("roleRoleTypeSelected")
	public void handleRoleRoleTypeSelected() {
		Role role = getRole();
		role.setRoleType(roleTypeSelectManager.getSelectedRecord());
	}
	
	public void activate() {
		initializeContext();
		Role role = BeanContext.getFromSession(getInstanceId());
		if (role == null)
			newRole();
		else editRole(role);
	}
	
	@Begin
	public void newRole() {
		try {
			Role role = create();
			initialize(role);
			show();
		} catch (Exception e) {
			handleException(e);
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
	
	@Begin
	public void editRole(Role role) {
		try {
			role = clone(role);
			initialize(role);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
		
	@Observer("org.aries.saveRole")
	public void saveRole() {
		setModule("Role");
		Role role = getRole();
		enrichRole(role);
		if (validate(role)) {
			if (isImmediate())
				processRole(role);
			outject("role", role);
			raiseEvent(actionEvent);
		}
	}
	
	public void processRole(Role role) {
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
			raiseEvent("org.aries.refreshRoleList");
			raiseEvent(actionEvent);
		} catch (Exception e) {
			handleException(e);
		}
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
	
	public boolean validate(Role role) {
		Validator validator = getValidator();
		boolean isValid = RoleUtil.validate(role);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
