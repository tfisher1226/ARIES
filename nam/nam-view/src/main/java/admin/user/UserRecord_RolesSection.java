package admin.user;

import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.RoleUtil;


@SuppressWarnings("serial")
@Named("userRolesSection")
public class UserRecord_RolesSection extends AbstractWizardPage<User> {

	//private static Log log = LogFactory.getLog(UserSetupPage.class);
	
	private User user;

	
	public UserRecord_RolesSection() {
		//setTitle("Specify user information.");
		setName("Roles");
		setUrl("roles");
		//setOwner(owner);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void initialize(User user) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUser(user);
	}
	
	public void validate() {
		if (user == null) {
			validator.missing("User");
		} else {
			if (RoleUtil.isEmpty(user.getRoles()))
				validator.missing("Roles");
		}
	}

}
