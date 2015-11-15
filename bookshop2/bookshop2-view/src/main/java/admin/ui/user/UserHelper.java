package admin.ui.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.common.util.PersonNameUtil;
import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import admin.Permission;
import admin.Role;
import admin.User;
import admin.ui.permission.PermissionListManager;
import admin.ui.permission.PermissionListObject;
import admin.ui.role.RoleListManager;
import admin.ui.role.RoleListObject;
import admin.util.UserUtil;


@SessionScoped
@Named("userHelper")
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
		RoleListManager roleListManager = BeanContext.getFromSession("roleListManager");
		DataModel<RoleListObject> dataModel = roleListManager.getDataModel(rolesList);
		return dataModel;
	}
	
}
