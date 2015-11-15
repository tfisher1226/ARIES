package admin.registration;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.SelectionContext;

import org.aries.runtime.BeanContext;

import admin.Registration;
import admin.User;
import admin.client.userService.UserService;


@SessionScoped
@Named("registrationDataManager")
public class RegistrationDataManager implements Serializable {
	
	@Inject
	private RegistrationEventManager registrationEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Registration> getRegistrationList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		return getDefaultList();
	}
	
	public Collection<Registration> getDefaultList() {
		return null;
	}
	
	public void saveRegistration(Registration registration) {
		User user = registration.getUser();
		BeanContext.set("org.aries.user", user);
		if (scope != null) {
			Object owner = getOwner();
			//save in database table
			UserService userService = BeanContext.getFromSession(UserService.ID); 
			userService.saveUser(user);
		}
	}
	
	public boolean removeRegistration(Registration registration) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
