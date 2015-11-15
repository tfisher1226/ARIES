package nam.model.language;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Language;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("languageEventManager")
public class LanguageEventManager extends AbstractEventManager<Language> implements Serializable {
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public Language getInstance() {
		return selectionContext.getSelection("language");
	}
	
	public void removeLanguage() {
		Language language = getInstance();
		fireRemoveEvent(language);
	}
	
}
