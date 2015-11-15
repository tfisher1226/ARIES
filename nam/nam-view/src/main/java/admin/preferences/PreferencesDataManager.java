package admin.preferences;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Preferences;
import admin.util.PreferencesUtil;

import nam.ui.design.SelectionContext;


@SessionScoped
@Named("preferencesDataManager")
public class PreferencesDataManager implements Serializable {
	
	@Inject
	private PreferencesEventManager preferencesEventManager;
	
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
	
	public Collection<Preferences> getPreferencesList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Preferences> getDefaultList() {
		return null;
	}
	
	public void savePreferences(Preferences preferences) {
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removePreferences(Preferences preferences) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
