package admin.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Registration;

import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("registrationEventManager")
public class RegistrationEventManager extends AbstractEventManager<Registration> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Registration getInstance() {
		return selectionContext.getSelection("registration");
	}
	
	public void removeRegistration() {
		Registration registration = getInstance();
		fireRemoveEvent(registration);
	}
	
}
