package admin.user;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.wizard.AbstractWizardPanel;

import admin.User;


public class UserRecord_ContactPanel extends AbstractWizardPanel {

	private User user;

	
	public UserRecord_ContactPanel(String module) {
		setMessage("Specify Contact Information");
		setModule(module);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void initialize(User user) {
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setUser(user);
	}
	
	public boolean validate() {
		if (user == null) {
			validator.missing("User");
		} else {
			//if (user.getConfiguration() == null)
			//	validator.missing("Configuration");
		}
		//for now
		return true;
	}
	
}
