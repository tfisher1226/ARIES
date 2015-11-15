package admin.ui.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.model.DataModel;

import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.common.util.PersonNameUtil;
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
import admin.User;
import admin.ui.permission.PermissionListManager;
import admin.ui.permission.PermissionListObject;
import admin.ui.role.RoleListManager;
import admin.ui.role.RoleListObject;
import admin.util.UserUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Name("userHelper")
@Scope(ScopeType.SESSION)
public class UserHelper extends AbstractElementHelper<User> implements Serializable {

	@Override
	public boolean isEmpty(User user) {
		return UserUtil.isEmpty(user);
	}
	
	@Override
	public String toString(User user) {
		return PersonNameUtil.toPersonNameString(user.getPersonName());
	}
	
	@Override
	public String toString(Collection<User> userList) {
		return UserUtil.toString(userList);
	}
	
	@Override
	public boolean validate(User user) {
		return UserUtil.validate(user);
	}

	@Override
	public boolean validate(Collection<User> userList) {
		return UserUtil.validate(userList);
	}
	
	public Map<PhoneLocation, PhoneNumber> getPhoneNumbers(User user) {
		if (user == null)
			return null;
		Map<PhoneLocation, PhoneNumber> map = new HashMap<PhoneLocation, PhoneNumber>();
		PhoneNumber cellPhone = user.getCellPhone();
		PhoneNumber homePhone = user.getHomePhone();
		if (cellPhone != null)
			map.put(PhoneLocation.CELL, cellPhone);
		if (homePhone != null)
			map.put(PhoneLocation.HOME, homePhone);
		return map;
	}
	
	public DataModel<PermissionListObject> getPermissions(User user) {
		if (user == null)
			return null;
		return getPermissions(user.getPermissions());
	}

	public DataModel<PermissionListObject> getPermissions(Collection<Permission> permissionsList) {
		PermissionListManager permissionListManager = BeanContext.getFromSession("permissionListManager");
		DataModel<PermissionListObject> dataModel = permissionListManager.getDataModel(permissionsList);
		return dataModel;
	}
	
	public DataModel<RoleListObject> getRoles(User user) {
		if (user == null)
			return null;
		return getRoles(user.getRoles());
	}
	
	public DataModel<RoleListObject> getRoles(Collection<Role> rolesList) {
		RoleListManager roleListManager = BeanContext.getFromSession("mainRoleListManager");
		DataModel<RoleListObject> dataModel = roleListManager.getDataModel(rolesList);
		return dataModel;
	}
	
}
