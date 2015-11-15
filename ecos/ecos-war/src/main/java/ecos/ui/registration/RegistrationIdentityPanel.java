package ecos.ui.registration;

import java.io.Serializable;

import org.aries.ui.wizard.AbstractWizardPanel;

import admin.Registration;


@SuppressWarnings("serial")
public class RegistrationIdentityPanel extends AbstractWizardPanel implements Serializable {
	
	private Registration registration;

	
	public RegistrationIdentityPanel(String module) {
		setMessage("Specify Contact Information");
		setModule(module);
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public void initialize(Registration registration) {
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setRegistration(registration);
	}
	
	public boolean validate() {
		if (registration == null) {
			validator.missing("Registration");
		} else {
			//if (StringUtils.isEmpty(registration.getGroupId()))
			//	validator.missing("Group ID");
			//if (StringUtils.isEmpty(registration.getArtifactId()))
			//	validator.missing("Artifact ID");
			//if (StringUtils.isEmpty(registration.getVersion()))
			//	validator.missing("Version");
			//if (registration.getWebEnabled() != null && registration.getWebEnabled() && StringUtils.isEmpty(registration.getNamespace())) 
			//	validator.missing("Namespace");
		}
		//for now
		return true;
	}
	
}
