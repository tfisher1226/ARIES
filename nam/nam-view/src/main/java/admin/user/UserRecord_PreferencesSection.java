package admin.user;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.PreferencesUtil;


@SessionScoped
@Named("userPreferencesSection")
public class UserRecord_PreferencesSection extends AbstractWizardPage<User> {
	
	private User user;

	
	public UserRecord_PreferencesSection() {
		setName("Preferences");
		setUrl("preferences");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void initialize(User user) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUser(user);
	}
	
	@Override
	public void validate() {
		if (user == null) {
			validator.missing("User");
		} else {
			if (PreferencesUtil.isEmpty(user.getPreferences()))
				validator.missing("Preferences");
		}
	}

}
