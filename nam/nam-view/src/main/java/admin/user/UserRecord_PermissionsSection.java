package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.PermissionUtil;


@SessionScoped
@Named("userPermissionsSection")
public class UserRecord_PermissionsSection extends AbstractWizardPage<User> implements Serializable {
	
	private User user;

	
	public UserRecord_PermissionsSection() {
		setName("Permissions");
		setUrl("permissions");
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void initialize(User user) {
		setUser(user);
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		super.initialize(user);
	}
	
	@Override
	public void validate() {
		if (user == null) {
			validator.missing("User");
		} else {
			if (PermissionUtil.isValid(user.getPermissions()))
				validator.invalid("permissions", "Permissions not valid");
		}
	}

}
