package nam.model.language;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Language;
import nam.model.Project;
import nam.model.util.LanguageUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("languageInfoManager")
public class LanguageInfoManager extends AbstractNamRecordManager<Language> implements Serializable {
	
	@Inject
	private LanguageWizard languageWizard;
	
	@Inject
	private LanguageDataManager languageDataManager;
	
	@Inject
	private LanguagePageManager languagePageManager;
	
	@Inject
	private LanguageEventManager languageEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private LanguageHelper languageHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public LanguageInfoManager() {
		setInstanceName("language");
	}
	
	
	public Language getLanguage() {
		return getRecord();
	}
	
	public Language getSelectedLanguage() {
		return selectionContext.getSelection("language");
	}
	
	@Override
	public Class<Language> getRecordClass() {
		return Language.class;
	}
	
	@Override
	public boolean isEmpty(Language language) {
		return languageHelper.isEmpty(language);
	}
	
	@Override
	public String toString(Language language) {
		return languageHelper.toString(language);
	}
	
	@Override
	public void initialize() {
		Language language = selectionContext.getSelection("language");
		if (language != null)
			initialize(language);
	}
	
	protected void initialize(Language language) {
		LanguageUtil.initialize(language);
		languageWizard.initialize(language);
		setContext("language", language);
	}
	
	public void handleLanguageSelected(@Observes @Selected Language language) {
		selectionContext.setSelection("language",  language);
		languagePageManager.updateState(language);
		languagePageManager.refreshMembers();
		setRecord(language);
	}
	
	@Override
	public String newRecord() {
		return newLanguage();
	}
	
	public String newLanguage() {
		try {
			Language language = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("language",  language);
			String url = languagePageManager.initializeLanguageCreationPage(language);
			languagePageManager.pushContext(languageWizard);
			initialize(language);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Language create() {
		Language language = LanguageUtil.create();
		return language;
	}
	
	@Override
	public Language clone(Language language) {
		language = LanguageUtil.clone(language);
		return language;
	}
	
	@Override
	public String viewRecord() {
		return viewLanguage();
	}
	
	public String viewLanguage() {
		Language language = selectionContext.getSelection("language");
		String url = viewLanguage(language);
		return url;
	}
	
	public String viewLanguage(Language language) {
		try {
			String url = languagePageManager.initializeLanguageSummaryView(language);
			languagePageManager.pushContext(languageWizard);
			initialize(language);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editLanguage();
	}
	
	public String editLanguage() {
		Language language = selectionContext.getSelection("language");
		String url = editLanguage(language);
		return url;
	}
	
	public String editLanguage(Language language) {
		try {
			//language = clone(language);
			selectionContext.resetOrigin();
			selectionContext.setSelection("language",  language);
			String url = languagePageManager.initializeLanguageUpdatePage(language);
			languagePageManager.pushContext(languageWizard);
			initialize(language);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveLanguage() {
		Language language = getLanguage();
		if (validateLanguage(language)) {
			saveLanguage(language);
		}
	}
	
	public void persistLanguage(Language language) {
		saveLanguage(language);
	}
	
	public void saveLanguage(Language language) {
		try {
			saveLanguageToSystem(language);
			languageEventManager.fireAddedEvent(language);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveLanguageToSystem(Language language) {
		languageDataManager.saveLanguage(language);
	}
	
	public void handleSaveLanguage(@Observes @Add Language language) {
		saveLanguage(language);
	}
	
	public void enrichLanguage(Language language) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Language language) {
		return validateLanguage(language);
	}
	
	public boolean validateLanguage(Language language) {
		Validator validator = getValidator();
		boolean isValid = LanguageUtil.validate(language);
		Display display = getFromSession("display");
		display.setModule("languageInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveLanguage() {
		display = getFromSession("display");
		display.setModule("languageInfo");
		Language language = selectionContext.getSelection("language");
		if (language == null) {
			display.error("Language record must be selected.");
		}
	}
	
	public String handleRemoveLanguage(@Observes @Remove Language language) {
		display = getFromSession("display");
		display.setModule("languageInfo");
		try {
			display.info("Removing Language "+LanguageUtil.getLabel(language)+" from the system.");
			removeLanguageFromSystem(language);
			selectionContext.clearSelection("language");
			languageEventManager.fireClearSelectionEvent();
			languageEventManager.fireRemovedEvent(language);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeLanguageFromSystem(Language language) {
		if (languageDataManager.removeLanguage(language))
			setRecord(null);
	}
	
	public void cancelLanguage() {
		BeanContext.removeFromSession("language");
		languagePageManager.removeContext(languageWizard);
	}
	
}
