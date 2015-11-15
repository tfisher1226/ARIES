package nam.model.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Unselected;
import org.aries.util.Validator;


@SessionScoped
@Named("serviceInfoManager")
public class ServiceInfoManager extends AbstractNamRecordManager<Service> implements Serializable {
	
	@Inject
	private ServiceWizard serviceWizard;
	
	@Inject
	private ServiceDataManager serviceDataManager;
	
	@Inject
	private ServicePageManager servicePageManager;
	
	@Inject
	private ServiceEventManager serviceEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ServiceHelper serviceHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ServiceInfoManager() {
		setInstanceName("service");
	}
	
	
	public Service getService() {
		return getRecord();
	}
	
	public Service getSelectedService() {
		return selectionContext.getSelection("service");
	}
	
	@Override
	public Class<Service> getRecordClass() {
		return Service.class;
	}
	
	@Override
	public boolean isEmpty(Service service) {
		return serviceHelper.isEmpty(service);
	}
	
	@Override
	public String toString(Service service) {
		return serviceHelper.toString(service);
	}

	@Override
	public void initialize() {
		Service service = selectionContext.getSelection("service");
		if (service != null)
			initialize(service);
	}
	
	protected void initialize(Service service) {
		ServiceUtil.initialize(service);
		serviceWizard.initialize(service);
		setContext("service", service);
	}
	
	public void handleServiceSelected(@Observes @Selected Service service) {
		selectionContext.setSelection("service",  service);
		servicePageManager.refreshMembers("serviceSelection");
		servicePageManager.updateState(service);
		setRecord(service);
	}
	
	public void handleServiceUnselected(@Observes @Unselected Service service) {
		selectionContext.unsetSelection("service",  service);
		servicePageManager.refreshMembers("serviceSelection");
		unsetRecord(service);
	}
	
	@Override
	public String newRecord() {
		return newService();
	}
	
	public String newService() {
		try {
			Service service = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("service", service);
			String url = servicePageManager.initializeServiceCreationPage(service);
			servicePageManager.pushContext(serviceWizard);
			initialize(service);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Service create() {
		Service service = ServiceUtil.create();
		return service;
	}
	
	@Override
	public Service clone(Service service) {
		service = ServiceUtil.clone(service);
		return service;
	}
	
	@Override
	public String viewRecord() {
		return viewService();
	}
	
	public String viewService() {
		Service service = selectionContext.getSelection("service");
		String url = viewService(service);
		return url;
	}
	
	public String viewService(Service service) {
		try {
			String url = servicePageManager.initializeServiceSummaryView(service);
			servicePageManager.pushContext(serviceWizard);
			initialize(service);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editService();
	}
	
	public String editService() {
		Service service = selectionContext.getSelection("service");
		String url = editService(service);
		return url;
	}
	
	public String editService(Service service) {
		try {
			//service = clone(service);
			selectionContext.resetOrigin();
			selectionContext.setSelection("service", service);
			String url = servicePageManager.initializeServiceUpdatePage(service);
			servicePageManager.pushContext(serviceWizard);
			initialize(service);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveService() {
		Service service = getService();
		if (validateService(service)) {
			if (isImmediate())
				persistService(service);
			outject("service", service);
		}
	}
	
	public void persistService(Service service) {
		saveService(service);
	}
	
	public void saveService(Service service) {
		try {
			saveServiceToSystem(service);
			serviceEventManager.fireAddedEvent(service);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveServiceToSystem(Service service) {
		serviceDataManager.saveService(service);
	}
	
	public void handleSaveService(@Observes @Add Service service) {
		saveService(service);
	}
	
	public void addService(Service service) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichService(Service service) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Service service) {
		return validateService(service);
	}
	
	public boolean validateService(Service service) {
		Validator validator = getValidator();
		boolean isValid = ServiceUtil.validate(service);
		Display display = getFromSession("display");
		display.setModule("serviceInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveService() {
		display = getFromSession("display");
		display.setModule("serviceInfo");
		Service service = selectionContext.getSelection("service");
		if (service == null) {
			display.error("Service record must be selected.");
		}
	}
	
	public String handleRemoveService(@Observes @Remove Service service) {
		display = getFromSession("display");
		display.setModule("serviceInfo");
		try {
			display.info("Removing Service "+ServiceUtil.getLabel(service)+" from the system.");
			removeServiceFromSystem(service);
			selectionContext.clearSelection("service");
			serviceEventManager.fireClearSelectionEvent();
			serviceEventManager.fireRemovedEvent(service);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeServiceFromSystem(Service service) {
		if (serviceDataManager.removeService(service))
			setRecord(null);
	}
	
	public void cancelService() {
		BeanContext.removeFromSession("service");
		servicePageManager.removeContext(serviceWizard);
	}
	
}
