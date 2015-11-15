package ecos.ui.registration;

import java.io.Serializable;

import org.aries.runtime.BeanContext;
import org.aries.ui.Messages;
import org.aries.ui.wizard.AbstractWizard;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

import admin.Registration;


@AutoCreate
@Name("registrationWizard")
@Scope(ScopeType.CONVERSATION)
@SuppressWarnings("serial")
public class RegistrationWizard extends AbstractWizard implements Serializable {
	
	@Out(required=false)
	private Registration registration;

	@In(required=true)
	private RegistrationManager registrationManager;
	
	@Out(required=true)
	private RegistrationIdentityPanel registrationSetupPage;

	@Out(required=true)
	private RegistrationContactPanel registrationConfigPage;

	
	public RegistrationWizard() {
		registrationSetupPage = new RegistrationIdentityPanel("registrationWizard");
		registrationConfigPage = new RegistrationContactPanel("registrationWizard");
		addPage(registrationSetupPage);
		addPage(registrationConfigPage);
		//reset();
	}

	public void initialize(Registration registration) {
		registrationSetupPage.initialize(registration);
		registrationConfigPage.initialize(registration);
		registrationSetupPage.setVisible(true);
		registrationConfigPage.setVisible(false);
	}

//	Handler<Object> createRegistrationSelectListener() {
//		return new Handler<Object>() {
//			public void handle(Object artifact) {
//				if (artifact instanceof Registration) {
//					//Configuration configuration = (Configuration) artifact;
//					//registration.setConfiguration(configuration);
//				}
//			}
//		};
//	}

	public String refresh() {
		return super.refresh();
	}

	
//	public Registration getRegistration() {
//		return registration;
//	}
//
//	public void setRegistration(Registration registration) {
//		this.registration = registration;
//	}

	@Begin(join=true)
	public void newRegistration() {
		setTitle("New Registration");
		Messages messages = BeanContext.get("messages");
		messages.info("Registration", "Enter information for new Registration.");
		registration = new Registration();
		//registration.setUserId(value);
		registrationManager.initialize(registration);
		initialize(registration);
	}

//	@Begin(join=true)
//	public void editRegistration() {
//		editRegistration(registration);
//	}
	
	@Begin(join=true)
	public void editRegistration(Registration registration) {
		//setTitle("Registration: "+registration.getName()+"");
		//initialize(registration);
	}
	
	public void saveRegistration() {
		//List<Registration> registrations = ProjectUtil.getRegistrations(project);
		//if (!registrations.contains(registration))
		//	registrations.add(registration);
		//saveProject();
	}
	
	@Override
	public void finish() {
		super.finish();
		registrationManager.saveRegistration();
		refresh();
	}
	
}

