package admin.preferences;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Preferences;
import admin.util.PreferencesUtil;


@SessionScoped
@Named("preferencesOverviewSection")
public class PreferencesRecord_OverviewSection extends AbstractWizardPage<Preferences> implements Serializable {
	
	private Preferences preferences;
	
	
	public PreferencesRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
