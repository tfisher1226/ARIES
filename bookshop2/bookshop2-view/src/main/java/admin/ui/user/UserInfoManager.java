package admin.ui.user;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.manager.EmailAddressManager;
import org.aries.ui.manager.PersonNameManager;
import org.aries.ui.manager.PhoneNumberManager;
import org.aries.ui.manager.StreetAddressManager;
import org.aries.util.CollectionUtil;
import org.aries.util.Validator;

import admin.Permission;
import admin.User;
import admin.client.userService.UserService;
import admin.ui.permission.PermissionInfoManager;
import admin.ui.permission.PermissionListManager;
import admin.ui.preferences.PreferencesInfoManager;
import admin.ui.role.RoleListManager;
import admin.ui.role.RoleSelectManager;
import admin.util.UserUtil;


@SessionScoped
@Named("userInfoManager")
public class UserInfoManager extends AbstractRecordManager<User> implements Serializable {

	private EmailAddressManager emailAddressManager;

	private PermissionInfoManager permissionInfoManager;
	
	private PermissionListManager permissionListManager;

	private PersonNameManager personNameManager;
	
	private PhoneNumberManager phoneNumberManager;

	private PreferencesInfoManager preferencesInfoManager;
	
	private RoleSelectManager roleSelectManager;
	
	private RoleListManager roleListManager;
	
	private StreetAddressManager streetAddressManager;

	
	public UserInfoManager() {
		setInstanceName("user");
	}

	
	public User getUser() {
		return getRecord();
	}
	
	@Override
	public Class<User> getRecordClass() {
		return User.class;
	}
	
	@Override
	public boolean isEmpty(User user) {
		return getUserHelper().isEmpty(user);
	}
	
	@Override
	public String toString(User user) {
		return getUserHelper().toString(user);
	}
	
	protected UserHelper getUserHelper() {
		return BeanContext.getFromSession("userHelper");
	}
	
	protected UserService getUserService() {
		return BeanContext.getFromSession(UserService.ID);
	}
	
	public void initialize(User user) {
		UserUtil.initialize(user);
		initializeEmailAddressManager(user);
		initializePermissionInfoManager(user);
		initializePermissionListManager(user);
		initializePersonNameManager(user);
		initializePhoneNumberManager(user);
		initializePreferencesInfoManager(user);
		initializeRoleListManager(user);
		initializeRoleSelectManager(user);
		initializeStreetAddressManager(user);
		initializeOutjectedState(user);
		setContext("User", user);
	}
	
	protected void initializeOutjectedState(User user) {
		outjectTo("userEmailAddress", user.getEmailAddress());
		//outjectTo("userName", user.getName());
		outjectTo("userPermissions", user.getPermissions());
		outjectTo("userPersonName", user.getPersonName());
		outjectTo("userPhoneNumbers", getUserHelper().getPhoneNumbers(user));
		outjectTo("userPreferences", user.getPreferences());
		outjectTo("userRoles", user.getRoles());
		outjectTo("userStreetAddress", user.getStreetAddress());
		outject(instanceName, user);
	}

	protected void initializeEmailAddressManager(User user) {
		emailAddressManager = BeanContext.getFromSession("emailAddressManager");
		emailAddressManager.setContext("User", toString(user));
		emailAddressManager.initialize();
	}
	
	protected void initializePermissionInfoManager(User user) {
		permissionInfoManager = BeanContext.getFromSession("permissionInfoManager");
		permissionInfoManager.setContext("User", toString(user));
		permissionInfoManager.initialize();
	}

	protected void initializePermissionListManager(User user) {
		permissionListManager = BeanContext.getFromSession("permissionListManager");
		permissionListManager.setContext("User", toString(user));
		permissionListManager.initialize();
	}
	
	protected void initializePersonNameManager(User user) {
		personNameManager = BeanContext.getFromSession("personNameManager");
		personNameManager.setContext("User", toString(user));
		personNameManager.initialize();
	}
	
	protected void initializePhoneNumberManager(User user) {
		phoneNumberManager = BeanContext.getFromSession("phoneNumberManager");
		phoneNumberManager.setContext("User", toString(user));
		phoneNumberManager.initialize();
	}

	protected void initializePreferencesInfoManager(User user) {
		preferencesInfoManager = BeanContext.getFromSession("preferencesInfoManager");
		preferencesInfoManager.setContext("User", toString(user));
		preferencesInfoManager.initialize();
	}
	
	protected void initializeRoleListManager(User user) {
		roleListManager = BeanContext.getFromSession("roleListManager");
		roleListManager.setContext("User", toString(user));
		roleListManager.initialize();
	}

	protected void initializeRoleSelectManager(User user) {
		roleSelectManager = BeanContext.getFromSession("roleSelectManager");
		roleSelectManager.setContext("User", toString(user));
		roleSelectManager.setOwnerRecord(user);
		roleSelectManager.initialize();
	}
	
	protected void initializeStreetAddressManager(User user) {
		streetAddressManager = BeanContext.getFromSession("streetAddressManager");
		streetAddressManager.setContext("User", toString(user));
		streetAddressManager.initialize();
	}

	//SEAM @Observer("userEmailAddressEntered")
	public void handleUserEmailAddressEntered() {
		User user = getUser();
		user.setEmailAddress(emailAddressManager.getEmailAddress());
	}
	
//	//SEAM @Observer("userNameEntered")
//	public void handleUserNameEntered() {
//		User user = getUser();
//		user.setName(personNameManager.getPersonName());
//	}
	
	//SEAM @Observer("userPermissionsEntered")
	public void handleUserPermissionsEntered() {
		User user = getUser();
		Permission permission = permissionInfoManager.getPermission();
		CollectionUtil.add(user.getPermissions(), permission);
		permissionListManager.initialize(user.getPermissions());
	}
	
	//SEAM @Observer("userPersonNameEntered")
	public void handleUserPersonNameEntered() {
		User user = getUser();
		user.setPersonName(personNameManager.getPersonName());
	}
	
	//SEAM @Observer("userPhoneNumbersEntered")
	public void handleUserPhoneNumbersEntered() {
		User user = getUser();
		Map<PhoneLocation, PhoneNumber> phoneNumbers = phoneNumberManager.getPhoneNumberMap();
		user.setCellPhone(phoneNumbers.get(PhoneLocation.CELL));
		user.setHomePhone(phoneNumbers.get(PhoneLocation.HOME));
	}
	
	//SEAM @Observer("userPreferencesEntered")
	public void handleUserPreferencesEntered() {
		User user = getUser();
		user.setPreferences(preferencesInfoManager.getPreferences());
	}
	
	//SEAM @Observer("userRolesSelected")
	public void handleUserRolesSelected() {
		User user = getUser();
		user.getRoles().clear();
		user.getRoles().addAll(roleSelectManager.getSelectedRecordList());
	}
	
	//SEAM @Observer("userStreetAddressEntered")
	public void handleUserStreetAddressEntered() {
		User user = getUser();
		user.setStreetAddress(streetAddressManager.getStreetAddress());
	}

	public void activate() {
		initializeContext();
		User user = BeanContext.getFromSession(getInstanceId());
		if (user == null)
			newUser();
		else editUser(user);
	}
	
	//SEAM @Begin
	public void newUser() {
		try {
			User user = create();
			initialize(user);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}

	@Override
	public User create() {
		User user = UserUtil.create();
		return user;
	}

	@Override
	public User clone(User user) {
		user = UserUtil.clone(user);
		return user;
	}

	//SEAM @Begin
	public void editUser(User user) {
		try {
			user = clone(user);
			initialize(user);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	//SEAM @Observer("org.aries.saveUser")
	public void saveUser() {
		setModule("User");
		User user = getUser();
		enrichUser(user);
		if (validate(user)) {
			if (isImmediate())
				processUser(user);
			outject("user", user);
			raiseEvent(actionEvent);
		}
	}

	public void processUser(User user) {
		Long id = user.getId();
		if (id != null) {
			saveUser(user);
		} else {
			addUser(user);
		}
	}
	
	public void saveUser(User user) {
		try {
			getUserService().saveUser(user);
			raiseEvent("org.aries.refreshUserList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void addUser(User user) {
		try {
			Long id = getUserService().addUser(user);
			Assert.notNull(id, "User Id should not be null");
			raiseEvent("org.aries.refreshUserList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void enrichUser(User user) {
		//nothing for now
	}
	
	public boolean validate(User user) {
		Validator validator = getValidator();
		boolean isValid = UserUtil.validate(user);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
