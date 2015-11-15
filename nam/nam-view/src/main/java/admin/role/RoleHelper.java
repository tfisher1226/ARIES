package admin.role;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import admin.Permission;
import admin.Role;
import admin.permission.PermissionListManager;
import admin.permission.PermissionListObject;
import admin.util.RoleUtil;


@SessionScoped
@Named("roleHelper")
public class RoleHelper extends AbstractElementHelper<Role> implements Serializable {

	@Override
	public boolean isEmpty(Role role) {
		return RoleUtil.isEmpty(role);
	}
	
	@Override
	public String toString(Role role) {
		return RoleUtil.toString(role);
	}

	@Override
	public String toString(Collection<Role> roleList) {
		return RoleUtil.toString(roleList);
	}
	
	@Override
	public boolean validate(Role role) {
		return RoleUtil.validate(role);
	}
	
	@Override
	public boolean validate(Collection<Role> roleList) {
		return RoleUtil.validate(roleList);
	}
	
	public DataModel<RoleListObject> getGroups(Role role) {
		if (role == null)
			return null;
		return getGroups(role.getGroups());
	}
	
	public DataModel<RoleListObject> getGroups(Collection<Role> groupsList) {
		RoleListManager roleListManager = BeanContext.getFromSession("roleListManager");
		DataModel<RoleListObject> dataModel = roleListManager.getDataModel(groupsList);
		return dataModel;
	}
	
	public DataModel<PermissionListObject> getPermissions(Role role) {
		if (role == null)
			return null;
		return getPermissions(role.getPermissions());
	}
	
	public DataModel<PermissionListObject> getPermissions(Collection<Permission> permissionsList) {
		PermissionListManager permissionListManager = BeanContext.getFromSession("permissionListManager");
		DataModel<PermissionListObject> dataModel = permissionListManager.getDataModel(permissionsList);
		return dataModel;
	}
	
}
