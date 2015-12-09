package admin.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Registration;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationSubmitSection")
public class RegistrationRecord_SubmitSection extends AbstractWizardPage<Registration> implements Serializable {
	
	private Registration registration;
	
	
	public RegistrationRecord_SubmitSection() {
		setTitle("Choose your plan");
		setName("Submit");
		setUrl("submit");
	}
	
	
	public Registration getRegistration() {
		return registration;
	}
	
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	@Override
	public void initialize(Registration registration) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setRegistration(registration);
	}
	
	@Override
	public void validate() {
		if (registration == null) {
			validator.missing("Registration");
		} else {
		}
	}
	
}
