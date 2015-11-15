package nam.model.language;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Language;
import nam.model.util.LanguageUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("languageDataManager")
public class LanguageDataManager implements Serializable {
	
	@Inject
	private LanguageEventManager languageEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Language> getLanguageList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Language> getDefaultList() {
		return null;
	}
	
	public void saveLanguage(Language language) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeLanguage(Language language) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
