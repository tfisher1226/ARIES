package admin.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.RoleUtil;


@SessionScoped
@Named("userRolesSection")
public class UserRecord_RolesSection extends AbstractWizardPage<User> implements Serializable {
	
	private User user;

	
	public UserRecord_RolesSection() {
		setName("Roles");
		setUrl("roles");
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
			if (RoleUtil.isEmpty(user.getRoles()))
				validator.missing("Roles");
		}
	}

}
