package admin.registration;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.util.PersonNameUtil;
import org.aries.ui.AbstractWizardPage;

import admin.Registration;


@SessionScoped
@Named("registrationContactSection")
public class RegistrationRecord_ContactSection extends AbstractWizardPage<Registration> {

	private Registration registration;

	
	public RegistrationRecord_ContactSection() {
		setName("Contact");
		setUrl("contact");
	}
	
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public String getLabel() {
		return PersonNameUtil.toPersonNameString(registration.getUser().getPersonName());
	}
	
	public void initialize(Registration registration) {
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setRegistration(registration);
	}
	
	public void validate() {
		if (registration == null) {
			validator.missing("Registration");
		} else {
//			if (!EmailAddressUtil.isEmpty(registration.getEmailAddress()) && !EmailAddressUtil.isValid(registration.getEmailAddress()))
//				validator.invalid("emailAddress", "Email Address not valid");
//			if (!StreetAddressUtil.isEmpty(registration.getStreetAddress()) && !StreetAddressUtil.isValid(registration.getStreetAddress()))
//				validator.invalid("streetAddress", "Street Address");
//			if (!PhoneNumberUtil.isEmpty(registration.getCellPhone()) && !PhoneNumberUtil.isValid(registration.getCellPhone()))
//				validator.invalid("cellPhone", "Cell Phone");
//			if (!PhoneNumberUtil.isEmpty(registration.getHomePhone()) && !PhoneNumberUtil.isValid(registration.getHomePhone()))
//				validator.invalid("homePhone", "Home Phone");
		}
	}
	
}
