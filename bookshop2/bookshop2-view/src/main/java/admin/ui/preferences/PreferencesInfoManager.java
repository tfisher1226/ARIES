package admin.ui.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.util.Validator;

import admin.Preferences;
import admin.ui.user.UserSelectManager;
import admin.util.PreferencesUtil;

@SessionScoped
@Named("preferencesInfoManager")
public class PreferencesInfoManager extends AbstractRecordManager<Preferences> implements Serializable {
	
	private UserSelectManager userSelectManager;
	
	
	public PreferencesInfoManager() {
		setInstanceName("preferences");
	}


	public Preferences getPreferences() {
		return getRecord();
	}

	@Override
	public Class<Preferences> getRecordClass() {
		return Preferences.class;
	}
	
	@Override
	public boolean isEmpty(Preferences preferences) {
		return getPreferencesHelper().isEmpty(preferences);
	}
	
	@Override
	public String toString(Preferences preferences) {
		return getPreferencesHelper().toString(preferences);
	}
	
	protected PreferencesHelper getPreferencesHelper() {
		return BeanContext.getFromSession("preferencesHelper");
	}
	
	protected void initialize(Preferences preferences) {
		PreferencesUtil.initialize(preferences);
		initializeUserSelectManager(preferences);
		initializeOutjectedState(preferences);
		setContext("Preferences", preferences);
	}
	
	protected void initializeOutjectedState(Preferences preferences) {
		outjectTo("preferencesUser", preferences.getUser());
		outject(instanceName, preferences);
	}

	protected void initializeUserSelectManager(Preferences preferences) {
		userSelectManager = BeanContext.getFromSession("userSelectManager");
		userSelectManager.setContext("Preferences", toString(preferences));
		userSelectManager.setOwnerRecord(preferences);
		userSelectManager.initialize();
	}

	//SEAM @Observer("preferencesUserSelected")
	public void handlePreferencesUserSelected() {
		Preferences preferences = getPreferences();
		preferences.setUser(userSelectManager.getSelectedRecord());
	}
	
	public void activate() {
		initializeContext();
		Preferences preferences = BeanContext.getFromSession(getInstanceId());
		if (preferences == null)
			newPreferences();
		else editPreferences(preferences);
	}

	//SEAM @Begin(join = true)
	public void newPreferences() {
		try {
			Preferences preferences = create();
		initialize(preferences);
			show();
		} catch (Exception e) {
			handleException(e);
	}
	}

	@Override
	public Preferences create() {
		Preferences preferences = PreferencesUtil.create();
		return preferences;
	}
	
	@Override
	public Preferences clone(Preferences preferences) {
		preferences = PreferencesUtil.clone(preferences);
		return preferences;
	}
	
	//SEAM @Begin(join = true)
	public void editPreferences(Preferences preferences) {
		try {
			preferences = clone(preferences);
		initialize(preferences);
			show();
		} catch (Exception e) {
			handleException(e);
	}
	}

	//SEAM @Observer("org.aries.savePreferences")
	public void savePreferences() {
		setModule("Preferences");
		Preferences preferences = getPreferences();
		enrichPreferences(preferences);
		if (validate(preferences)) {
			savePreferences(preferences);
		}
	}

	public void processPreferences(Preferences preferences) {
		Long id = preferences.getId();
		if (id != null) {
			savePreferences(preferences);
		}
	}
	
	public void savePreferences(Preferences preferences) {
		try {
			raiseEvent("org.aries.refreshPreferencesList");
			raiseEvent(actionEvent);
			
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void enrichPreferences(Preferences preferences) {
		//nothing for now
	}
	
	public boolean validate(Preferences preferences) {
		Validator validator = getValidator();
		boolean isValid = PreferencesUtil.validate(preferences);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
