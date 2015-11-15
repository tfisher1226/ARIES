package admin.role;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractInvocationManager;
import org.aries.ui.InvocationValues;

import admin.Role;
import admin.RoleType;
import admin.client.roleService.RoleService;
import admin.client.roleService.RoleServiceClient;
import admin.roleType.RoleTypeSelectManager;


@SessionScoped
@Named("roleServiceManager")
public class RoleServiceManager extends AbstractInvocationManager implements Serializable {
	
	public RoleServiceManager() {
		initializeGetRoleList();
		initializeGetRoleById();
		initializeGetRoleByName();
		initializeGetRoleListByRoleType();
		initializeAddRole();
		initializeSaveRole();
		initializeDeleteRole();
	}
	
	
	protected RoleService getRoleService() {
		RoleServiceClient roleService = BeanContext.getFromSession(RoleService.ID);
		roleService.setTransportType(getTransportType());
		return roleService;
	}
	
	protected RoleInfoManager getRoleInfoManager() {
		return BeanContext.getFromSession("roleInfoManager");
	}
	
	protected RoleListManager getRoleListManager() {
		return BeanContext.getFromSession("roleListManager");
	}
	
	protected RoleSelectManager getRoleSelectManager() {
		return BeanContext.getFromSession("roleSelectManager");
	}
	
	protected RoleTypeSelectManager getRoleTypeSelectManager() {
		return BeanContext.getFromSession("roleTypeSelectManager");
	}
	
	public void initializeGetRoleList() {
		InvocationValues invocationValues = getInvocationValues("getRoleList");
		invocationValues.addResultListPlaceholder("Role", "roleList");
	}
	
	public void initializeGetRoleById() {
		InvocationValues invocationValues = getInvocationValues("getRoleById");
		invocationValues.addParameterPlaceholder("Long", "id");
		invocationValues.addResultPlaceholder("Role", "role");
	}
	
	public void initializeGetRoleByName() {
		InvocationValues invocationValues = getInvocationValues("getRoleByName");
		invocationValues.addParameterPlaceholder("String", "name");
		invocationValues.addResultPlaceholder("Role", "role");
	}
	
	public void initializeGetRoleListByRoleType() {
		InvocationValues invocationValues = getInvocationValues("getRoleListByRoleType");
		invocationValues.addParameterPlaceholder("RoleType", "roleType");
		invocationValues.addResultListPlaceholder("Role", "roleList");
	}
	
	public void initializeAddRole() {
		InvocationValues invocationValues = getInvocationValues("addRole");
		invocationValues.addParameterPlaceholder("Role", "role");
		invocationValues.addResultPlaceholder("Long", "id");
	}
	
	public void initializeSaveRole() {
		InvocationValues invocationValues = getInvocationValues("saveRole");
		invocationValues.addParameterPlaceholder("Role", "role");
	}
	
	public void initializeDeleteRole() {
		InvocationValues invocationValues = getInvocationValues("deleteRole");
		invocationValues.addParameterPlaceholder("Role", "role");
	}
	
	//SEAM @Observer("roleEntered")
	public void handleRoleEntered() {
		RoleInfoManager manager = getRoleInfoManager();
		String serviceId = manager.getTargetService();
		Role role = manager.getRole();
		setParameterValue(serviceId, "role", role);
	}
	
	//SEAM @Observer("roleSelected")
	public void handleRoleSelected() {
		RoleSelectManager manager = getRoleSelectManager();
		String serviceId = manager.getTargetService();
		Role selectedRole = manager.getSelectedRecord();
		setParameterValue(serviceId, "role", selectedRole);
	}
	
	//SEAM @Observer("roleListSelected")
	public void handleRoleListSelected() {
		RoleSelectManager manager = getRoleSelectManager();
		String serviceId = manager.getTargetService();
		Collection<Role> selectedRoleList = manager.getSelectedRecords();
		setParameterValue(serviceId, "roleList", selectedRoleList);
	}
	
	//SEAM @Observer("roleTypeSelected")
	public void handleRoleTypeSelected() {
		RoleTypeSelectManager manager = getRoleTypeSelectManager();
		String serviceId = manager.getTargetService();
		RoleType selectedRoleType = manager.getSelectedRecord();
		setParameterValue(serviceId, "roleType", selectedRoleType);
	}
	
	//SEAM @Observer("roleTypeListSelected")
	public void handleRoleTypeListSelected() {
		RoleTypeSelectManager manager = getRoleTypeSelectManager();
		String serviceId = manager.getTargetService();
		Collection<RoleType> selectedRoleTypeList = manager.getSelectedRecords();
		setParameterValue(serviceId, "roleTypeList", selectedRoleTypeList);
	}
	
	public void executeGetRoleList() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			List<Role> roleList = getRoleService().getRoleList();
			Assert.notNull(roleList, "RoleList result(s) not found");
			invocationValues.setResultValue(roleList);
			outject("roleList", roleList);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetRoleById() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Long id = invocationValues.getParameterValue("id");
			Assert.notNull(id, "Id parameter must be specified");
			Role role = getRoleService().getRoleById(id);
			Assert.notNull(role, "Role result not found");
			invocationValues.setResultValue(role);
			outject("role", role);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetRoleByName() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			String name = invocationValues.getParameterValue("name");
			Assert.notNull(name, "Name parameter must be specified");
			Role role = getRoleService().getRoleByName(name);
			Assert.notNull(role, "Role result not found");
			invocationValues.setResultValue(role);
			outject("role", role);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetRoleListByRoleType() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			RoleType roleType = invocationValues.getParameterValue("roleType");
			Assert.notNull(roleType, "RoleType parameter must be specified");
			List<Role> roleList = getRoleService().getRoleListByRoleType(roleType);
			Assert.notNull(roleList, "RoleList result(s) not found");
			invocationValues.setResultValue(roleList);
			outject("roleList", roleList);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeAddRole() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Role role = invocationValues.getParameterValue("role");
			Assert.notNull(role, "Role parameter must be specified");
			Long id = getRoleService().addRole(role);
			Assert.notNull(id, "Id result not found");
			invocationValues.setResultValue(id);
			outject("id", id);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeSaveRole() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Role role = invocationValues.getParameterValue("role");
			Assert.notNull(role, "Role parameter must be specified");
			getRoleService().saveRole(role);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeDeleteRole() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Role role = invocationValues.getParameterValue("role");
			Assert.notNull(role, "Role parameter must be specified");
			getRoleService().deleteRole(role);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
}
