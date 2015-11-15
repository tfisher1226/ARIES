package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;

import admin.User;
import admin.client.userService.UserService;
import admin.util.UserUtil;


@SessionScoped
@Named("userInfoManager")
public class UserInfoManager extends AbstractNamRecordManager<User> implements Serializable {

	@Inject
	private UserWizard userWizard;
	
	@Inject
	private UserDataManager userDataManager;
	
	@Inject
	private UserPageManager userPageManager;
	
	@Inject
	private UserEventManager userEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private UserHelper userHelper;

	@Inject
	private SelectionContext selectionContext;
	
	
	public UserInfoManager() {
		setInstanceName("user");
	}

	
	public User getUser() {
		return getRecord();
	}
	
	public User getSelectedUser() {
		return selectionContext.getSelection("user");
	}
	
	@Override
	public Class<User> getRecordClass() {
		return User.class;
	}
	
	@Override
	public boolean isEmpty(User user) {
		return userHelper.isEmpty(user);
	}
	
	@Override
	public String toString(User user) {
		return userHelper.toString(user);
	}
	
	protected UserService getUserService() {
		return BeanContext.getFromSession(UserService.ID);
	}
	
	@Override
	public void initialize() {
		User user = selectionContext.getSelection("user");
		if (user != null)
			initialize(user);
	}
	
	protected void initialize(User user) {
		UserUtil.initialize(user);
		userWizard.initialize(user);
		setContext("user", user);
	}

	public void handleUserSelected(@Observes @Selected User user) {
		selectionContext.setSelection("user",  user);
		userPageManager.updateState(user);
		userPageManager.refreshMembers();
		setRecord(user);
	}

	@Override
	public String newRecord() {
		return newUser();
	}
	
	public String newUser() {
		try {
			User user = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("user",  user);
			String url = userPageManager.initializeUserCreationPage(user);
			userPageManager.pushContext(userWizard);
			initialize(user);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
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

	@Override
	public String viewRecord() {
		return viewUser();
	}
	
	public String viewUser() {
		User user = selectionContext.getSelection("user");
		String url = viewUser(user);
		return url;
	}
	
	public String viewUser(User user) {
		try {
			String url = userPageManager.initializeUserSummaryView(user);
			userPageManager.pushContext(userWizard);
			initialize(user);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editUser();
	}
	
	public String editUser() {
		User user = selectionContext.getSelection("user");
		String url = editUser(user);
		return url;
	}
	
	public String editUser(User user) {
		try {
			//user = clone(user);
			selectionContext.resetOrigin();
			selectionContext.setSelection("user",  user);
			String url = userPageManager.initializeUserUpdatePage(user);
			userPageManager.pushContext(userWizard);
			initialize(user);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveUser() {
		User user = getUser();
		if (validateUser(user)) {
			if (isImmediate())
				persistUser(user);
			outject("user", user);
		}
	}

	public void persistUser(User user) {
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
		} catch (Exception e) {
			handleException(e);
		}
	}

	protected void saveUserToSystem(User user) {
		userDataManager.saveUser(user);
	}
	
	public void handleSaveUser(@Observes @Add User user) {
		saveUser(user);
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
	
	@Override
	public boolean validate(User user) {
		return validateUser(user);
	}
	
	public boolean validateUser(User user) {
		Validator validator = getValidator();
		boolean isValid = UserUtil.validate(user);
		Display display = getFromSession("display");
		display.setModule("userInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveUser() {
		display = getFromSession("display");
		display.setModule("userInfo");
		User user = selectionContext.getSelection("user");
		if (user == null) {
			display.error("User record must be selected.");
		}
	}
	
	public String handleRemoveUser(@Observes @Remove User user) {
		display = getFromSession("display");
		display.setModule("userInfo");
		try {
			display.info("Removing User "+UserUtil.getLabel(user)+" from the system.");
			removeUserFromSystem(user);
			selectionContext.clearSelection("user");
			userEventManager.fireClearSelectionEvent();
			userEventManager.fireRemovedEvent(user);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeUserFromSystem(User user) {
		if (userDataManager.removeUser(user))
			setRecord(null);
	}
	
	public void cancelUser() {
		BeanContext.removeFromSession("user");
		userPageManager.removeContext(userWizard);
	}
	
}
