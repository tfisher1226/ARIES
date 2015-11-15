package admin.user;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.PermissionUtil;


@SessionScoped
@Named("userPermissionsSection")
public class UserRecord_PermissionsSection extends AbstractWizardPage<User> {

	//private static Log log = LogFactory.getLog(UserSetupPage.class);
	
	private User user;

	
	public UserRecord_PermissionsSection() {
		//setTitle("Specify user information.");
		setName("Permissions");
		setUrl("permissions");
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
			if (PermissionUtil.isValid(user.getPermissions()))
				validator.invalid("permissions", "Permissions not valid");
		}
	}

}
