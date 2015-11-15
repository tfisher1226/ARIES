package admin.ui.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractInvocationManager;
import org.aries.ui.InvocationValues;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.User;
import admin.client.userService.UserService;
import admin.client.userService.UserServiceClient;


@Startup
@AutoCreate
@BypassInterceptors
@Name("userServiceManager")
@Scope(ScopeType.SESSION)
public class UserServiceManager extends AbstractInvocationManager implements Serializable {

	public UserServiceManager() {
		initializeGetUserList();
		initializeGetUserById();
		initializeGetUserByUserName();
		initializeAddUser();
		initializeSaveUser();
		initializeDeleteUser();
	}
	
	
	protected UserService getUserService() {
		UserServiceClient userService = BeanContext.getFromSession(UserService.ID);
		userService.setTransportType(getTransportType());
		return userService;
	}
	
	protected UserInfoManager getUserInfoManager() {
		return BeanContext.getFromSession("userInfoManager");
	}
	
	protected UserListManager getUserListManager() {
		return BeanContext.getFromSession("userListManager");
	}
	
	protected UserSelectManager getUserSelectManager() {
		return BeanContext.getFromSession("userSelectManager");
	}
	
	public void initializeGetUserList() {
		InvocationValues invocationValues = getInvocationValues("getUserList");
		invocationValues.addResultListPlaceholder("User", "userList");
	}
	
	public void initializeGetUserById() {
		InvocationValues invocationValues = getInvocationValues("getUserById");
		invocationValues.addParameterPlaceholder("Long", "id");
		invocationValues.addResultPlaceholder("User", "user");
	}

	public void initializeGetUserByUserName() {
		InvocationValues invocationValues = getInvocationValues("getUserByUserName");
		invocationValues.addParameterPlaceholder("String", "userName");
		invocationValues.addResultPlaceholder("User", "user");
	}
	
	public void initializeAddUser() {
		InvocationValues invocationValues = getInvocationValues("addUser");
		invocationValues.addParameterPlaceholder("User", "user");
		invocationValues.addResultPlaceholder("Long", "id");
	}
	
	public void initializeSaveUser() {
		InvocationValues invocationValues = getInvocationValues("saveUser");
		invocationValues.addParameterPlaceholder("User", "user");
	}
	
	public void initializeDeleteUser() {
		InvocationValues invocationValues = getInvocationValues("deleteUser");
		invocationValues.addParameterPlaceholder("User", "user");
	}

	@Observer("userEntered")
	public void handleUserEntered() {
		UserInfoManager manager = getUserInfoManager();
		String serviceId = manager.getTargetService();
		User user = manager.getUser();
		setParameterValue(serviceId, "user", user);
	}
	
	@Observer("userSelected")
	public void handleUserSelected() {
		UserSelectManager manager = getUserSelectManager();
		String serviceId = manager.getTargetService();
		User selectedUser = manager.getSelectedRecord();
		setParameterValue(serviceId, "user", selectedUser);
	}
	
	@Observer("userListSelected")
	public void handleUserListSelected() {
		UserSelectManager manager = getUserSelectManager();
		String serviceId = manager.getTargetService();
		Collection<User> selectedUserList = manager.getSelectedRecords();
		setParameterValue(serviceId, "userList", selectedUserList);
	}

	public void executeGetUserList() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			List<User> userList = getUserService().getUserList();
			Assert.notNull(userList, "UserList result(s) not found");
			invocationValues.setResultValue(userList);
			outject("userList", userList);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetUserById() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Long id = invocationValues.getParameterValue("id");
			Assert.notNull(id, "Id parameter must be specified");
			User user = getUserService().getUserById(id);
			Assert.notNull(user, "User result not found");
			invocationValues.setResultValue(user);
			outject("user", user);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetUserByUserName() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			String userName = invocationValues.getParameterValue("userName");
			Assert.notNull(userName, "UserName parameter must be specified");
			User user = getUserService().getUserByUserName(userName);
			Assert.notNull(user, "User result not found");
			invocationValues.setResultValue(user);
			outject("user", user);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeAddUser() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			User user = invocationValues.getParameterValue("user");
			Assert.notNull(user, "User parameter must be specified");
			Long id = getUserService().addUser(user);
			Assert.notNull(id, "Id result not found");
			invocationValues.setResultValue(id);
			outject("id", id);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeSaveUser() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			User user = invocationValues.getParameterValue("user");
			Assert.notNull(user, "User parameter must be specified");
			getUserService().saveUser(user);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeDeleteUser() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			User user = invocationValues.getParameterValue("user");
			Assert.notNull(user, "User parameter must be specified");
			getUserService().deleteUser(user);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
}
