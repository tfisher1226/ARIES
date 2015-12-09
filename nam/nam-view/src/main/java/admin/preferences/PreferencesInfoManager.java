package admin.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.util.Validator;

import admin.Preferences;
import admin.util.PreferencesUtil;


@SessionScoped
@Named("preferencesInfoManager")
public class PreferencesInfoManager extends AbstractNamRecordManager<Preferences> implements Serializable {
	
	@Inject
	private PreferencesWizard preferencesWizard;
	
	@Inject
	private PreferencesDataManager preferencesDataManager;
	
	@Inject
	private PreferencesPageManager preferencesPageManager;
	
	@Inject
	private PreferencesEventManager preferencesEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private PreferencesHelper preferencesHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public PreferencesInfoManager() {
		setInstanceName("preferences");
	}


	public Preferences getPreferences() {
		return getRecord();
	}

	public Preferences getSelectedPreferences() {
		return selectionContext.getSelection("preferences");
	}
	
	@Override
	public Class<Preferences> getRecordClass() {
		return Preferences.class;
	}
	
	@Override
	public boolean isEmpty(Preferences preferences) {
		return preferencesHelper.isEmpty(preferences);
	}
	
	@Override
	public String toString(Preferences preferences) {
		return preferencesHelper.toString(preferences);
	}
	
	@Override
	public void initialize() {
		Preferences preferences = selectionContext.getSelection("preferences");
		if (preferences != null)
			initialize(preferences);
	}
	
	protected void initialize(Preferences preferences) {
		preferencesWizard.initialize(preferences);
		setContext("preferences", preferences);
	}

	@Override
	public String newRecord() {
		return newPreferences();
	}

	public String newPreferences() {
		try {
			Preferences preferences = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("preferences",  preferences);
			String url = preferencesPageManager.initializePreferencesCreationPage(preferences);
			preferencesPageManager.pushContext(preferencesWizard);
		initialize(preferences);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
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
	
	@Override
	public String viewRecord() {
		return viewPreferences();
	}
	
	public String viewPreferences() {
		Preferences preferences = selectionContext.getSelection("preferences");
		String url = viewPreferences(preferences);
		return url;
	}
	
	public String viewPreferences(Preferences preferences) {
		try {
			String url = preferencesPageManager.initializePreferencesSummaryView(preferences);
			preferencesPageManager.pushContext(preferencesWizard);
			initialize(preferences);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editPreferences();
	}
	
	public String editPreferences() {
		Preferences preferences = selectionContext.getSelection("preferences");
		String url = editPreferences(preferences);
		return url;
	}
	
	public String editPreferences(Preferences preferences) {
		try {
			//preferences = clone(preferences);
			selectionContext.resetOrigin();
			selectionContext.setSelection("preferences",  preferences);
			String url = preferencesPageManager.initializePreferencesUpdatePage(preferences);
			preferencesPageManager.pushContext(preferencesWizard);
		initialize(preferences);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
	}
	}

	public void savePreferences() {
		Preferences preferences = getPreferences();
		if (validatePreferences(preferences)) {
			savePreferences(preferences);
		}
	}

	public void persistPreferences(Preferences preferences) {
		Long id = preferences.getId();
		if (id != null) {
			savePreferences(preferences);
		}
	}
	
	public void savePreferences(Preferences preferences) {
		try {
			savePreferencesToSystem(preferences);
			preferencesEventManager.fireAddedEvent(preferences);
		} catch (Exception e) {
			handleException(e);
		}
	}

	protected void savePreferencesToSystem(Preferences preferences) {
		preferencesDataManager.savePreferences(preferences);
	}
	
	public void handleSavePreferences(@Observes @Add Preferences preferences) {
		savePreferences(preferences);
	}
	
	public void enrichPreferences(Preferences preferences) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Preferences preferences) {
		return validatePreferences(preferences);
	}
	
	public boolean validatePreferences(Preferences preferences) {
		Validator validator = getValidator();
		boolean isValid = PreferencesUtil.validate(preferences);
		Display display = getFromSession("display");
		display.setModule("preferencesInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemovePreferences() {
		display = getFromSession("display");
		display.setModule("preferencesInfo");
		Preferences preferences = selectionContext.getSelection("preferences");
		if (preferences == null) {
			display.error("Preferences record must be selected.");
		}
	}
	
	public String handleRemovePreferences(@Observes @Remove Preferences preferences) {
		display = getFromSession("display");
		display.setModule("preferencesInfo");
		try {
			display.info("Removing Preferences "+PreferencesUtil.getLabel(preferences)+" from the system.");
			removePreferencesFromSystem(preferences);
			selectionContext.clearSelection("preferences");
			preferencesEventManager.fireClearSelectionEvent();
			preferencesEventManager.fireRemovedEvent(preferences);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removePreferencesFromSystem(Preferences preferences) {
		if (preferencesDataManager.removePreferences(preferences))
			setRecord(null);
	}
	
	public void cancelPreferences() {
		BeanContext.removeFromSession("preferences");
		preferencesPageManager.removeContext(preferencesWizard);
	}
	
}
