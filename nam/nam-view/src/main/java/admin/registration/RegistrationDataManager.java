package admin.registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import admin.Registration;
import admin.util.RegistrationUtil;

import nam.ui.design.SelectionContext;


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
		if (scope != null) {
			Object owner = getOwner();
		}
	}
	
	public boolean removeRegistration(Registration registration) {
		if (scope != null) {
			Object owner = getOwner();
		}
		return false;
	}
	
}
