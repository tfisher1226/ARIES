package admin.ui.permission;

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

import admin.Action;
import admin.Permission;
import admin.ui.action.ActionListManager;
import admin.ui.action.ActionListObject;
import admin.util.PermissionUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("permissionHelper")
@Scope(ScopeType.SESSION)
public class PermissionHelper extends AbstractElementHelper<Permission> implements Serializable {

	@Override
	public boolean isEmpty(Permission permission) {
		return PermissionUtil.isEmpty(permission);
	}
	
	@Override
	public String toString(Permission permission) {
		return PermissionUtil.toString(permission);
	}

	@Override
	public String toString(Collection<Permission> permissionList) {
		return PermissionUtil.toString(permissionList);
	}

	@Override
	public boolean validate(Permission permission) {
		return PermissionUtil.validate(permission);
	}
	
	@Override
	public boolean validate(Collection<Permission> permissionList) {
		return PermissionUtil.validate(permissionList);
	}

	public DataModel<ActionListObject> getActions(Permission permission) {
		if (permission == null)
			return null;
		return getActions(permission.getActions());
	}
	
	public DataModel<ActionListObject> getActions(Collection<Action> actionsList) {
		ActionListManager actionListManager = BeanContext.getFromSession("actionListManager");
		DataModel<ActionListObject> dataModel = actionListManager.getDataModel(actionsList);
		return dataModel;
	}

}
