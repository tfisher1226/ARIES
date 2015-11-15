package admin.user;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.PhoneNumberUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.ui.AbstractWizardPage;

import admin.User;
import admin.util.PermissionUtil;
import admin.util.PreferencesUtil;
import admin.util.RoleUtil;


@SessionScoped
@Named("userIdentitySection")
public class UserRecord_IdentitySection extends AbstractWizardPage<User> {

	//private static Log log = LogFactory.getLog(UserSetupPage.class);
	
	private User user;

	
	public UserRecord_IdentitySection() {
		//setTitle("Specify user information.");
		setName("Identity");
		setUrl("identity");
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
			if (PersonNameUtil.isEmpty(user.getPersonName()))
				validator.missing("Person Name");
			if (StringUtils.isEmpty(user.getUserName()))
				validator.missing("User Name");
			if (StringUtils.isEmpty(user.getPassword()))
				validator.missing("Password");
			if (StringUtils.isEmpty(user.getPassword2()))
				validator.missing("Password Confirmation");
//			if (PermissionUtil.isEmpty(user.getPermissions()))
//				validator.missing("Permissions");
//			if (PreferencesUtil.isEmpty(user.getPreferences()))
//				validator.missing("Preferences");
//			if (RoleUtil.isEmpty(user.getRoles()))
//				validator.missing("Roles");
		}
	}

}
