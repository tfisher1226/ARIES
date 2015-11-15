package admin.user;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.PhoneNumberUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.ui.AbstractWizardPage;

import admin.User;


@SessionScoped
@Named("userContactSection")
public class UserRecord_ContactSection extends AbstractWizardPage<User> {

	//private static Log log = LogFactory.getLog(UserConfigPage.class);

	private User user;

	
	public UserRecord_ContactSection() {
		//setTitle("Specify desired configuration.");
		setName("Contact");
		setUrl("contact");
		//setOwner(owner);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLabel() {
		return PersonNameUtil.toPersonNameString(user.getPersonName());
	}
	
	public void initialize(User user) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setUser(user);
	}
	
	public void validate() {
		if (user == null) {
			validator.missing("User");
		} else {
			if (!EmailAddressUtil.isEmpty(user.getEmailAddress()) && !EmailAddressUtil.isValid(user.getEmailAddress()))
				validator.invalid("emailAddress", "Email Address not valid");
			if (!StreetAddressUtil.isEmpty(user.getStreetAddress()) && !StreetAddressUtil.isValid(user.getStreetAddress()))
				validator.invalid("streetAddress", "Street Address");
			if (!PhoneNumberUtil.isEmpty(user.getCellPhone()) && !PhoneNumberUtil.isValid(user.getCellPhone()))
				validator.invalid("cellPhone", "Cell Phone");
			if (!PhoneNumberUtil.isEmpty(user.getHomePhone()) && !PhoneNumberUtil.isValid(user.getHomePhone()))
				validator.invalid("homePhone", "Home Phone");
		}
	}
	
}
