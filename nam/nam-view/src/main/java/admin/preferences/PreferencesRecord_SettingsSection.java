package admin.preferences;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.util.PersonNameUtil;
import org.aries.ui.AbstractWizardPage;

import admin.Preferences;


@SessionScoped
@Named("preferencesSettingsSection")
public class PreferencesRecord_SettingsSection extends AbstractWizardPage<Preferences> {

	//private static Log log = LogFactory.getLog(PreferencesConfigPage.class);

	private Preferences preferences;

	
	public PreferencesRecord_SettingsSection() {
		//setTitle("Specify desired configuration.");
		setName("Contact");
		setUrl("contact");
		//setOwner(owner);
	}
	
	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public String getLabel() {
		return PersonNameUtil.toPersonNameString(preferences.getUser().getPersonName());
	}
	
	public void initialize(Preferences preferences) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPreferences(preferences);
	}
	
	public void validate() {
		if (preferences == null) {
			validator.missing("Preferences");
		} else {
//			if (!EmailAddressUtil.isEmpty(preferences.getEmailAddress()) && !EmailAddressUtil.isValid(preferences.getEmailAddress()))
//				validator.invalid("emailAddress", "Email Address not valid");
//			if (!StreetAddressUtil.isEmpty(preferences.getStreetAddress()) && !StreetAddressUtil.isValid(preferences.getStreetAddress()))
//				validator.invalid("streetAddress", "Street Address");
//			if (!PhoneNumberUtil.isEmpty(preferences.getCellPhone()) && !PhoneNumberUtil.isValid(preferences.getCellPhone()))
//				validator.invalid("cellPhone", "Cell Phone");
//			if (!PhoneNumberUtil.isEmpty(preferences.getHomePhone()) && !PhoneNumberUtil.isValid(preferences.getHomePhone()))
//				validator.invalid("homePhone", "Home Phone");
		}
	}
	
}
