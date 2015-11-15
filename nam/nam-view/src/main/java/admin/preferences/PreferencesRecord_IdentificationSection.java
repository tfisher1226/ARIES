package admin.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Preferences;
import admin.util.PreferencesUtil;


@SessionScoped
@Named("preferencesIdentificationSection")
public class PreferencesRecord_IdentificationSection extends AbstractWizardPage<Preferences> implements Serializable {
	
	private Preferences preferences;
	
	
	public PreferencesRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Preferences getPreferences() {
		return preferences;
	}
	
	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}
	
	@Override
	public void initialize(Preferences preferences) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setPreferences(preferences);
	}
	
	@Override
	public void validate() {
		if (preferences == null) {
			validator.missing("Preferences");
		} else {
		}
	}
	
}
