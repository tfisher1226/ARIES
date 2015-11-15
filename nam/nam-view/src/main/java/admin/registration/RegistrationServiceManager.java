package admin.registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractInvocationManager;
import org.aries.ui.InvocationValues;

import admin.Registration;
import admin.client.registrationService.RegistrationService;
import admin.client.registrationService.RegistrationServiceClient;


@SessionScoped
@Named("registrationServiceManager")
public class RegistrationServiceManager extends AbstractInvocationManager implements Serializable {
	
	public RegistrationServiceManager() {
		initializeGetRegistrationList();
		initializeGetRegistrationById();
		initializeAddRegistration();
		initializeSaveRegistration();
		initializeDeleteRegistration();
	}
	
	
	protected RegistrationService getRegistrationService() {
		RegistrationServiceClient registrationService = BeanContext.getFromSession(RegistrationService.ID);
		registrationService.setTransportType(getTransportType());
		return registrationService;
	}
	
	protected RegistrationInfoManager getRegistrationInfoManager() {
		return BeanContext.getFromSession("registrationInfoManager");
	}
	
	protected RegistrationListManager getRegistrationListManager() {
		return BeanContext.getFromSession("registrationListManager");
	}
	
	protected RegistrationSelectManager getRegistrationSelectManager() {
		return BeanContext.getFromSession("registrationSelectManager");
	}
	
	public void initializeGetRegistrationList() {
		InvocationValues invocationValues = getInvocationValues("getRegistrationList");
		invocationValues.addResultListPlaceholder("Registration", "registrationList");
	}
	
	public void initializeGetRegistrationById() {
		InvocationValues invocationValues = getInvocationValues("getRegistrationById");
		invocationValues.addParameterPlaceholder("Long", "id");
		invocationValues.addResultPlaceholder("Registration", "registration");
	}
	
	public void initializeAddRegistration() {
		InvocationValues invocationValues = getInvocationValues("addRegistration");
		invocationValues.addParameterPlaceholder("Registration", "registration");
		invocationValues.addResultPlaceholder("Long", "id");
	}
	
	public void initializeSaveRegistration() {
		InvocationValues invocationValues = getInvocationValues("saveRegistration");
		invocationValues.addParameterPlaceholder("Registration", "registration");
	}
	
	public void initializeDeleteRegistration() {
		InvocationValues invocationValues = getInvocationValues("deleteRegistration");
		invocationValues.addParameterPlaceholder("Registration", "registration");
	}
	
	//SEAM @Observer("registrationEntered")
	public void handleRegistrationEntered() {
		RegistrationInfoManager manager = getRegistrationInfoManager();
		String serviceId = manager.getTargetService();
		Registration registration = manager.getRegistration();
		setParameterValue(serviceId, "registration", registration);
	}
	
	//SEAM @Observer("registrationSelected")
	public void handleRegistrationSelected() {
		RegistrationSelectManager manager = getRegistrationSelectManager();
		String serviceId = manager.getTargetService();
		Registration selectedRegistration = manager.getSelectedRecord();
		setParameterValue(serviceId, "registration", selectedRegistration);
	}
	
	//SEAM @Observer("registrationListSelected")
	public void handleRegistrationListSelected() {
		RegistrationSelectManager manager = getRegistrationSelectManager();
		String serviceId = manager.getTargetService();
		Collection<Registration> selectedRegistrationList = manager.getSelectedRecords();
		setParameterValue(serviceId, "registrationList", selectedRegistrationList);
	}
	
	public void executeGetRegistrationList() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			List<Registration> registrationList = getRegistrationService().getRegistrationList();
			Assert.notNull(registrationList, "RegistrationList result(s) not found");
			invocationValues.setResultValue(registrationList);
			outject("registrationList", registrationList);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeGetRegistrationById() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Long id = invocationValues.getParameterValue("id");
			Assert.notNull(id, "Id parameter must be specified");
			Registration registration = getRegistrationService().getRegistrationById(id);
			Assert.notNull(registration, "Registration result not found");
			invocationValues.setResultValue(registration);
			outject("registration", registration);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeAddRegistration() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Registration registration = invocationValues.getParameterValue("registration");
			Assert.notNull(registration, "Registration parameter must be specified");
			Long id = getRegistrationService().addRegistration(registration);
			Assert.notNull(id, "Id result not found");
			invocationValues.setResultValue(id);
			outject("id", id);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeSaveRegistration() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Registration registration = invocationValues.getParameterValue("registration");
			Assert.notNull(registration, "Registration parameter must be specified");
			getRegistrationService().saveRegistration(registration);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void executeDeleteRegistration() {
		try {
			InvocationValues invocationValues = getInvocationValues();
			Registration registration = invocationValues.getParameterValue("registration");
			Assert.notNull(registration, "Registration parameter must be specified");
			getRegistrationService().deleteRegistration(registration);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
}
