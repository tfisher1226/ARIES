package admin.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.common.util.EmailAddressUtil;
import org.aries.common.util.PersonNameUtil;
import org.aries.common.util.StreetAddressUtil;
import org.aries.ui.AbstractWizardPage;

import admin.Registration;
import admin.User;


@SessionScoped
@Named("registrationIdentificationSection")
public class RegistrationRecord_IdentificationSection extends AbstractWizardPage<Registration> implements Serializable {
	
	private Registration registration;
	
	
	public RegistrationRecord_IdentificationSection() {
		setTitle("Setup personal account");
		setName("Identification");
		setUrl("identification");
	}
	
	
	public Registration getRegistration() {
		return registration;
	}
	
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	@Override
	public void initialize(Registration registration) {
		setRegistration(registration);
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(true);
		//setPopulateVisible(true);
		//setPopulateEnabled(true);
		super.initialize(registration);
	}
	
	@Override
	public void validate() {
		if (registration == null) {
			validator.missing("Registration");
		} else {
			User user = registration.getUser();
			if (PersonNameUtil.isEmpty(user.getPersonName()))
				validator.missing("Person Name");
			if (StringUtils.isEmpty(user.getUserName()))
				validator.missing("User Name");
			if (StringUtils.isEmpty(user.getPassword()))
				validator.missing("Password Entry");
			if (StringUtils.isEmpty(user.getPassword2()))
				validator.missing("Password Confirmation");
			else if (!user.getPassword().equals(user.getPassword2()))
				validator.error("Passwords", "Passwords do not match");
			if (EmailAddressUtil.isEmpty(user.getEmailAddress()))
				validator.missing("Email Address");
			if (!EmailAddressUtil.isEmpty(user.getEmailAddress()) && !EmailAddressUtil.isValid(user.getEmailAddress()))
				validator.invalid("emailAddress", "Email Address");
			if (!StreetAddressUtil.isEmpty(user.getStreetAddress()) && !StreetAddressUtil.isValid(user.getStreetAddress()))
				validator.invalid("streetAddress", "Street Address");
		}
	}
	
}
