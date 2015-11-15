package ecos.ui.registration;

import org.aries.ui.wizard.AbstractManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

import admin.Registration;


@Startup
@AutoCreate
@Name("registrationManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class RegistrationManager extends AbstractManager {

	@In(required=false)
	@Out(required=false)
	private Registration registration;
	
	@Out(required=true)
	private RegistrationIdentityPanel registrationIdentityPanel;

	@Out(required=true)
	private RegistrationContactPanel registrationContactPanel;
	
	
	public RegistrationManager() {
		setTitle("New Registration");
		setHeader("New Registration");
		setRefreshEvent("registrationsChanged");
		registrationIdentityPanel = new RegistrationIdentityPanel("registrationManager");
		registrationContactPanel = new RegistrationContactPanel("registrationManager");
	}

	public String refresh() {
		return super.refresh();
	}
	
	public Registration getRegistration() {
		return registration;
	}

	public String getTitle() {
		return "Specify registration identication.";
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public void initialize(Registration registration) {
		registrationIdentityPanel.initialize(registration);
		registrationContactPanel.initialize(registration);
		registrationIdentityPanel.setVisible(true);
		registrationContactPanel.setVisible(true);
		this.registration = registration;
	}

	@Begin(join=true)
	public void editRegistration() {
		editRegistration(registration);
	}
	
	@Begin(join=true)
	public void editRegistration(Registration registration) {
		//setTitle("Registration: "+registration.getName()+"");
		//initialize(registration);
	}

	public void saveRegistration() {
		//if (registrationSetupPanel.isValid() &&
		//	registrationConfigPanel.isValid()) {
		//	saveProject();
		//}
	}

	@Override
	public String submit() {
		super.submit();
		saveRegistration();
		refresh();
		return null;
	}

}
