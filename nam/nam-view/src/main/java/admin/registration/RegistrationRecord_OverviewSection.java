package admin.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.ui.AbstractWizardPage;

import admin.Registration;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationOverviewSection")
public class RegistrationRecord_OverviewSection extends AbstractWizardPage<Registration> implements Serializable {
	
	private Registration registration;
	
	
	public RegistrationRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
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
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
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
