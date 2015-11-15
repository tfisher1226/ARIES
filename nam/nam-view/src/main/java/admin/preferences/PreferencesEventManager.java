package admin.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Preferences;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("preferencesEventManager")
public class PreferencesEventManager extends AbstractEventManager<Preferences> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Preferences getInstance() {
		return selectionContext.getSelection("preferences");
	}
	
	public void removePreferences() {
		Preferences preferences = getInstance();
		fireRemoveEvent(preferences);
	}
	
}
