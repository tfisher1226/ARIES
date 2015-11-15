package admin.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import admin.Preferences;
import admin.util.PreferencesUtil;


@SessionScoped
@Named("preferencesWizard")
@SuppressWarnings("serial")
public class PreferencesWizard extends AbstractDomainElementWizard<Preferences> implements Serializable {
	
	@Inject
	private PreferencesDataManager preferencesDataManager;
	
	@Inject
	private PreferencesPageManager preferencesPageManager;
	
	@Inject
	private PreferencesEventManager preferencesEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Preferences";
	}
	
	@Override
	public String getUrlContext() {
		return preferencesPageManager.getPreferencesWizardPage();
	}
	
	@Override
	public void initialize(Preferences preferences) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(preferencesPageManager.getSections());
		super.initialize(preferences);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		preferencesPageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		preferencesPageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		preferencesPageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		preferencesPageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Preferences preferences = getInstance();
		preferencesDataManager.savePreferences(preferences);
		preferencesEventManager.fireSavedEvent(preferences);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Preferences preferences = getInstance();
		//TODO take this out soon
		if (preferences == null)
			preferences = new Preferences();
		preferencesEventManager.fireCancelledEvent(preferences);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Preferences preferences = selectionContext.getSelection("preferences");
		String name = PreferencesUtil.getLabel(preferences);
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("preferencesWizard");
			display.error("Preferences name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
