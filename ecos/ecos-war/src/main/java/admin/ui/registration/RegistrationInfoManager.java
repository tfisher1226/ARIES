package admin.ui.registration;

import java.io.Serializable;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.util.Validator;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.ui.user.UserSelectManager;
import admin.util.RegistrationUtil;


@Startup
@AutoCreate
@BypassInterceptors
@Scope(ScopeType.SESSION)
@Name("registrationInfoManager")
public class RegistrationInfoManager extends AbstractRecordManager<Registration> implements Serializable {
	
	private UserSelectManager userSelectManager;
	
	
	public RegistrationInfoManager() {
		setInstanceName("registration");
	}
	
	
	public Registration getRegistration() {
		return getRecord();
	}
	
	@Override
	public Class<Registration> getRecordClass() {
		return Registration.class;
	}
	
	@Override
	public boolean isEmpty(Registration registration) {
		return getRegistrationHelper().isEmpty(registration);
	}
	
	@Override
	public String toString(Registration registration) {
		return getRegistrationHelper().toString(registration);
	}
	
	protected RegistrationHelper getRegistrationHelper() {
		return BeanContext.getFromSession("registrationHelper");
	}
	
	protected RegistrationService getRegistrationService() {
		return BeanContext.getFromSession(RegistrationService.ID);
	}
	
	protected void initialize(Registration registration) {
		RegistrationUtil.initialize(registration);
		//initializeUserSelectManager(registration);
		initializeOutjectedState(registration);
		setContext("Registration", registration);
	}
	
	protected void initializeOutjectedState(Registration registration) {
		outjectTo("registrationUser", registration.getUser());
		outject(instanceName, registration);
	}
	
	protected void initializeUserSelectManager(Registration registration) {
		userSelectManager = BeanContext.getFromSession("userSelectManager");
		userSelectManager.setContext("Registration", toString(registration));
		userSelectManager.setOwnerRecord(registration);
		userSelectManager.initialize();
	}
	
	@Observer("registrationUserSelected")
	public void handleRegistrationUserSelected() {
		Registration registration = getRegistration();
		registration.setUser(userSelectManager.getSelectedRecord());
	}
	
	public void activate() {
		initializeContext();
		Registration registration = BeanContext.getFromSession(getInstanceId());
		if (registration == null)
			newRegistration();
		else editRegistration(registration);
	}
	
	@Begin
	public void newRegistration() {
		try {
			Registration registration = create();
			initialize(registration);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Override
	public Registration create() {
		Registration registration = RegistrationUtil.create();
		return registration;
	}
	
	@Override
	public Registration clone(Registration registration) {
		registration = RegistrationUtil.clone(registration);
		return registration;
	}
	
	@Begin
	public void editRegistration(Registration registration) {
		try {
			registration = clone(registration);
			initialize(registration);
			show();
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	@Observer("org.aries.saveRegistration")
	public void saveRegistration() {
		setModule("Registration");
		Registration registration = getRegistration();
		enrichRegistration(registration);
		if (validate(registration)) {
			if (isImmediate())
				processRegistration(registration);
			outject("registration", registration);
			raiseEvent(actionEvent);
		}
	}
	
	public void processRegistration(Registration registration) {
		Long id = registration.getId();
		if (id != null) {
			saveRegistration(registration);
		} else {
			addRegistration(registration);
		}
	}
	
	public void saveRegistration(Registration registration) {
		try {
			getRegistrationService().saveRegistration(registration);
			raiseEvent("org.aries.refreshRegistrationList");
			raiseEvent(actionEvent);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void addRegistration(Registration registration) {
		try {
			Long id = getRegistrationService().addRegistration(registration);
			Assert.notNull(id, "Registration Id should not be null");
			raiseEvent("org.aries.refreshRegistrationList");
			raiseEvent(actionEvent);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichRegistration(Registration registration) {
		//nothing for now
	}
	
	public boolean validate(Registration registration) {
		Validator validator = getValidator();
		boolean isValid = RegistrationUtil.validate(registration);
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
}
