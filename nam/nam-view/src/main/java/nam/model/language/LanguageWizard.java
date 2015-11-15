package nam.model.language;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Language;
import nam.model.Project;
import nam.model.util.LanguageUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("languageWizard")
@SuppressWarnings("serial")
public class LanguageWizard extends AbstractDomainElementWizard<Language> implements Serializable {
	
	@Inject
	private LanguageDataManager languageDataManager;
	
	@Inject
	private LanguagePageManager languagePageManager;
	
	@Inject
	private LanguageEventManager languageEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getName() {
		return "Language";
	}
	
	@Override
	public String getUrlContext() {
		return languagePageManager.getLanguageWizardPage();
	}
	
	@Override
	public void initialize(Language language) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(languagePageManager.getSections());
		super.initialize(language);
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
		languagePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		languagePageManager.updateState();
		return url;
	}
	
	@Override
	public String back() {
		String url = super.back();
		languagePageManager.updateState();
		return url;
	}
	
	@Override
	public String next() {
		String url = super.next();
		languagePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public String finish() {
		Language language = getInstance();
		languageDataManager.saveLanguage(language);
		languageEventManager.fireSavedEvent(language);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Language language = getInstance();
		//TODO take this out soon
		if (language == null)
			language = new Language();
		languageEventManager.fireCancelledEvent(language);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	public String populateDefaultValues() {
		Language language = selectionContext.getSelection("language");
		String name = language.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("languageWizard");
			display.error("Language name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
