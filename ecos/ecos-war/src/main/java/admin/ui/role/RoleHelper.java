package admin.ui.role;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.DataModel;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Permission;
import admin.Role;
import admin.ui.permission.PermissionListManager;
import admin.ui.permission.PermissionListObject;
import admin.util.RoleUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("roleHelper")
@Scope(ScopeType.SESSION)
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
		RoleListManager roleListManager = BeanContext.getFromSession("mainRoleListManager");
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
