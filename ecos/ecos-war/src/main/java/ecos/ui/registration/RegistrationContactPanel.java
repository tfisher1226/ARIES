package ecos.ui.registration;

import org.aries.ui.wizard.AbstractWizardPanel;

import admin.Registration;


@SuppressWarnings("serial")
public class RegistrationContactPanel extends AbstractWizardPanel {

	private Registration registration;

	
	public RegistrationContactPanel(String module) {
		setMessage("Specify Contact Information");
		setModule(module);
	}
	
	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public String getConfigurationName() {
		//Configuration configuration = registration.getConfiguration();
		//return configuration != null ? configuration.getGroupId()+"."+configuration.getArtifactId() : null;
		return null;
	}
	
	public void setConfigurationName(String configurationName) {
		//TODO this.configuration;
	}

	public void initialize(Registration registration) {
		setBackEnabled(true);
		setNextEnabled(false);
		setFinishEnabled(true);
		setRegistration(registration);
	}
	
	public boolean validate() {
		if (registration == null) {
			validator.missing("Registration");
		} else {
			//if (registration.getConfiguration() == null)
			//	validator.missing("Configuration");
		}
		//for now
		return true;
	}
	
}
