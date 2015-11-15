package nam.ui.userInterface;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.UserInterface;
import nam.ui.UserInterfaceType;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.userInterfaceType.UserInterfaceTypeSelectManager;
import nam.ui.util.UserInterfaceUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("userInterfaceInfoManager")
public class UserInterfaceInfoManager extends AbstractNamRecordManager<UserInterface> implements Serializable {
	
	@Inject
	private UserInterfaceWizard userInterfaceWizard;
	
	@Inject
	private UserInterfaceDataManager userInterfaceDataManager;
	
	@Inject
	private UserInterfacePageManager userInterfacePageManager;
	
	@Inject
	private UserInterfaceEventManager userInterfaceEventManager;
	
	@Inject
	private UserInterfaceTypeSelectManager userInterfaceTypeSelectManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private UserInterfaceHelper userInterfaceHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public UserInterfaceInfoManager() {
		setInstanceName("userInterface");
	}
	
	
	public UserInterface getUserInterface() {
		return getRecord();
	}
	
	public UserInterface getSelectedUserInterface() {
		return selectionContext.getSelection("userInterface");
	}
	
	@Override
	public Class<UserInterface> getRecordClass() {
		return UserInterface.class;
	}
	
	@Override
	public boolean isEmpty(UserInterface userInterface) {
		return userInterfaceHelper.isEmpty(userInterface);
	}
	
	@Override
	public String toString(UserInterface userInterface) {
		return userInterfaceHelper.toString(userInterface);
	}
	
	protected UserInterfaceHelper getUserInterfaceHelper() {
		return BeanContext.getFromSession("userInterfaceHelper");
	}
	
	@Override
	public void initialize() {
		UserInterface userInterface = selectionContext.getSelection("userInterface");
		if (userInterface != null)
			initialize(userInterface);
	}
	
	protected void initialize(UserInterface userInterface) {
		UserInterfaceUtil.initialize(userInterface);
		userInterfaceWizard.initialize(userInterface);
		initializeUserInterfaceTypeSelectManager(userInterface);
		initializeOutjectedState(userInterface);
		setContext("userInterface", userInterface);
	}
	
	protected void initializeOutjectedState(UserInterface userInterface) {
		outjectTo("userInterfaceType", userInterface.getType());
		outject("userInterface", userInterface);
	}
	
	protected void initializeUserInterfaceTypeSelectManager(UserInterface userInterface) {
		userInterfaceTypeSelectManager = BeanContext.getFromSession("userInterfaceTypeSelectManager");
		userInterfaceTypeSelectManager.setContext("UserInterface", toString(userInterface));
		userInterfaceTypeSelectManager.setOwnerRecord(userInterface);
		userInterfaceTypeSelectManager.initialize();
	}
	
	public void handleUserInterfaceTypeSelected(@Observes @Selected UserInterfaceType type) {
		getUserInterface().setType(type);
	}
	
	public void handleUserInterfaceSelected(@Observes @Selected UserInterface userInterface) {
		selectionContext.setSelection("userInterface",  userInterface);
		userInterfacePageManager.updateState(userInterface);
		userInterfacePageManager.refreshMembers();
		setRecord(userInterface);
	}
	
	@Override
	public String newRecord() {
		return newUserInterface();
	}
	
	public String newUserInterface() {
		try {
			UserInterface userInterface = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("userInterface",  userInterface);
			String url = userInterfacePageManager.initializeUserInterfaceCreationPage(userInterface);
			userInterfacePageManager.pushContext(userInterfaceWizard);
			initialize(userInterface);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public UserInterface create() {
		UserInterface userInterface = UserInterfaceUtil.create();
		return userInterface;
	}
	
	@Override
	public UserInterface clone(UserInterface userInterface) {
		userInterface = UserInterfaceUtil.clone(userInterface);
		return userInterface;
	}
	
	@Override
	public String viewRecord() {
		return viewUserInterface();
	}
	
	public String viewUserInterface() {
		UserInterface userInterface = selectionContext.getSelection("userInterface");
		String url = viewUserInterface(userInterface);
		return url;
	}
	
	public String viewUserInterface(UserInterface userInterface) {
		try {
			String url = userInterfacePageManager.initializeUserInterfaceSummaryView(userInterface);
			userInterfacePageManager.pushContext(userInterfaceWizard);
			initialize(userInterface);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editUserInterface();
	}
	
	public String editUserInterface() {
		UserInterface userInterface = selectionContext.getSelection("userInterface");
		String url = editUserInterface(userInterface);
		return url;
	}
	
	public String editUserInterface(UserInterface userInterface) {
		try {
			//userInterface = clone(userInterface);
			selectionContext.resetOrigin();
			selectionContext.setSelection("userInterface",  userInterface);
			String url = userInterfacePageManager.initializeUserInterfaceUpdatePage(userInterface);
			userInterfacePageManager.pushContext(userInterfaceWizard);
			initialize(userInterface);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveUserInterface() {
		UserInterface userInterface = getUserInterface();
		if (validateUserInterface(userInterface)) {
			if (isImmediate())
				persistUserInterface(userInterface);
			outject("userInterface", userInterface);
		}
	}
	
	public void persistUserInterface(UserInterface userInterface) {
		saveUserInterface(userInterface);
	}
	
	public void saveUserInterface(UserInterface userInterface) {
		try {
			saveUserInterfaceToSystem(userInterface);
			userInterfaceEventManager.fireAddedEvent(userInterface);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveUserInterfaceToSystem(UserInterface userInterface) {
		userInterfaceDataManager.saveUserInterface(userInterface);
	}
	
	public void handleSaveUserInterface(@Observes @Add UserInterface userInterface) {
		saveUserInterface(userInterface);
	}
	
	public void addUserInterface(UserInterface userInterface) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichUserInterface(UserInterface userInterface) {
		//nothing for now
	}
	
	@Override
	public boolean validate(UserInterface userInterface) {
		return validateUserInterface(userInterface);
	}
	
	public boolean validateUserInterface(UserInterface userInterface) {
		Validator validator = getValidator();
		boolean isValid = UserInterfaceUtil.validate(userInterface);
		Display display = getFromSession("display");
		display.setModule("userInterfaceInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveUserInterface() {
		display = getFromSession("display");
		display.setModule("userInterfaceInfo");
		UserInterface userInterface = selectionContext.getSelection("userInterface");
		if (userInterface == null) {
			display.error("UserInterface record must be selected.");
		}
	}
	
	public String handleRemoveUserInterface(@Observes @Remove UserInterface userInterface) {
		display = getFromSession("display");
		display.setModule("userInterfaceInfo");
		try {
			display.info("Removing UserInterface "+UserInterfaceUtil.getLabel(userInterface)+" from the system.");
			removeUserInterfaceFromSystem(userInterface);
			selectionContext.clearSelection("userInterface");
			userInterfaceEventManager.fireClearSelectionEvent();
			userInterfaceEventManager.fireRemovedEvent(userInterface);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeUserInterfaceFromSystem(UserInterface userInterface) {
		if (userInterfaceDataManager.removeUserInterface(userInterface))
			setRecord(null);
	}
	
	public void cancelUserInterface() {
		BeanContext.removeFromSession("userInterface");
		userInterfacePageManager.removeContext(userInterfaceWizard);
	}
	
}
