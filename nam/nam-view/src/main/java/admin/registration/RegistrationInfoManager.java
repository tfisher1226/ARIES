package admin.registration;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.Assert;
import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.util.RegistrationUtil;


@SessionScoped
@Named("registrationInfoManager")
public class RegistrationInfoManager extends AbstractNamRecordManager<Registration> implements Serializable {
	
	@Inject
	private RegistrationWizard registrationWizard;
	
	@Inject
	private RegistrationDataManager registrationDataManager;
	
	@Inject
	private RegistrationPageManager registrationPageManager;
	
	@Inject
	private RegistrationEventManager registrationEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private RegistrationHelper registrationHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public RegistrationInfoManager() {
		setInstanceName("registration");
	}
	
	
	public Registration getRegistration() {
		return getRecord();
	}
	
	public Registration getSelectedRegistration() {
		return selectionContext.getSelection("registration");
	}
	
	@Override
	public Class<Registration> getRecordClass() {
		return Registration.class;
	}
	
	@Override
	public boolean isEmpty(Registration registration) {
		return registrationHelper.isEmpty(registration);
	}
	
	@Override
	public String toString(Registration registration) {
		return registrationHelper.toString(registration);
	}
	
	protected RegistrationService getRegistrationService() {
		return BeanContext.getFromSession(RegistrationService.ID);
	}
	
	@Override
	public void initialize() {
		Registration registration = selectionContext.getSelection("registration");
		if (registration != null)
			initialize(registration);
	}
	
	protected void initialize(Registration registration) {
		registrationWizard.initialize(registration);
		setContext("registration", registration);
	}
	
	public void handleUserEmailAddressUpdated(@Observes @Updated EmailAddress emailAddress) {
		getRegistration().getUser().setEmailAddress(emailAddress);
	}
	
	public void handleUserPersonNameUpdated(@Observes @Updated PersonName personName) {
		getRegistration().getUser().setPersonName(personName);
	}
	
	@Override
	public String newRecord() {
		return newRegistration();
	}
	
	public String newRegistration() {
		try {
			Registration registration = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("registration",  registration);
			String url = registrationPageManager.initializeRegistrationCreationPage(registration);
			registrationPageManager.pushContext(registrationWizard);
			initialize(registration);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
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
	
	@Override
	public String viewRecord() {
		return viewRegistration();
	}
	
	public String viewRegistration() {
		Registration registration = selectionContext.getSelection("registration");
		String url = viewRegistration(registration);
		return url;
	}
	
	public String viewRegistration(Registration registration) {
		try {
			String url = registrationPageManager.initializeRegistrationSummaryView(registration);
			registrationPageManager.pushContext(registrationWizard);
			initialize(registration);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editRegistration();
	}
	
	public String editRegistration() {
		Registration registration = selectionContext.getSelection("registration");
		String url = editRegistration(registration);
		return url;
	}
	
	public String editRegistration(Registration registration) {
		try {
			//registration = clone(registration);
			selectionContext.resetOrigin();
			selectionContext.setSelection("registration",  registration);
			String url = registrationPageManager.initializeRegistrationUpdatePage(registration);
			registrationPageManager.pushContext(registrationWizard);
			initialize(registration);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveRegistration() {
		Registration registration = getRegistration();
		if (validateRegistration(registration)) {
			if (isImmediate())
				persistRegistration(registration);
			outject("registration", registration);
		}
	}
	
	public void persistRegistration(Registration registration) {
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
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveRegistrationToSystem(Registration registration) {
		registrationDataManager.saveRegistration(registration);
	}
	
	public void handleSaveRegistration(@Observes @Add Registration registration) {
		saveRegistration(registration);
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
	
	@Override
	public boolean validate(Registration registration) {
		return validateRegistration(registration);
	}
	
	public boolean validateRegistration(Registration registration) {
		Validator validator = getValidator();
		boolean isValid = RegistrationUtil.validate(registration);
		Display display = getFromSession("display");
		display.setModule("registrationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveRegistration() {
		display = getFromSession("display");
		display.setModule("registrationInfo");
		Registration registration = selectionContext.getSelection("registration");
		if (registration == null) {
			display.error("Registration record must be selected.");
		}
	}
	
	public String handleRemoveRegistration(@Observes @Remove Registration registration) {
		display = getFromSession("display");
		display.setModule("registrationInfo");
		try {
			display.info("Removing Registration "+RegistrationUtil.getLabel(registration)+" from the system.");
			removeRegistrationFromSystem(registration);
			selectionContext.clearSelection("registration");
			registrationEventManager.fireClearSelectionEvent();
			registrationEventManager.fireRemovedEvent(registration);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeRegistrationFromSystem(Registration registration) {
		if (registrationDataManager.removeRegistration(registration))
			setRecord(null);
	}

	public void cancelRegistration() {
		BeanContext.removeFromSession("registration");
		registrationPageManager.removeContext(registrationWizard);
	}
	
}
