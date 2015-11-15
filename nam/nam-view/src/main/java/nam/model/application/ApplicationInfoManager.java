package nam.model.application;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.util.ApplicationUtil;
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
@Named("applicationInfoManager")
public class ApplicationInfoManager extends AbstractNamRecordManager<Application> implements Serializable {

	@Inject
	private ApplicationWizard applicationWizard;
	
	@Inject
	private ApplicationDataManager applicationDataManager;
	
	@Inject
	private ApplicationPageManager applicationPageManager;

	@Inject
	private ApplicationEventManager applicationEventManager;

	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ApplicationHelper applicationHelper;
	
	@Inject
	private SelectionContext selectionContext;

	
	public ApplicationInfoManager() {
		setInstanceName("application");
	}
	
	
	public Application getApplication() {
		return getRecord();
	}

	public Application getSelectedApplication() {
		return selectionContext.getSelection("application");
	}
	
	@Override
	public Class<Application> getRecordClass() {
		return Application.class;
	}
	
	@Override
	public boolean isEmpty(Application application) {
		return applicationHelper.isEmpty(application);
	}
	
	@Override
	public String toString(Application application) {
		return applicationHelper.toString(application);
	}
	
	@Override
	public void initialize() {
		Application application = selectionContext.getSelection("application");
		if (application != null)
			initialize(application);
	}
	
	protected void initialize(Application application) {
		ApplicationUtil.initialize(application);
		applicationWizard.initialize(application);
		setContext("application", application);
	}

	public void handleApplicationSelected(@Observes @Selected Application application) {
		selectionContext.setSelection("application",  application);
		applicationPageManager.refreshMembers("applicationSelection");
		applicationPageManager.updateState(application);
		setRecord(application);
	}

	public void handleApplicationUnselected(@Observes @Unselected Application application) {
		selectionContext.unsetSelection("application",  application);
		applicationPageManager.refreshMembers("applicationSelection");
		unsetRecord(application);
	}
	
	@Override
	public String newRecord() {
		return newApplication();
	}
	
	public String newApplication() {
		try {
			Application application = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("application", application);
			String url = applicationPageManager.initializeApplicationCreationPage(application);
			applicationPageManager.pushContext(applicationWizard);
			initialize(application);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Application create() {
		Application application = ApplicationUtil.create();
		return application;
	}
	
	@Override
	public Application clone(Application application) {
		application = ApplicationUtil.clone(application);
		return application;
	}

	@Override
	public String viewRecord() {
		return viewApplication();
	}
	
	public String viewApplication() {
		Application application = selectionContext.getSelection("application");
		String url = viewApplication(application);
		return url;
	}
	
	public String viewApplication(Application application) {
		try {
			String url = applicationPageManager.initializeApplicationSummaryView(application);
			applicationPageManager.pushContext(applicationWizard);
			initialize(application);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editApplication();
	}
	
	public String editApplication() {
		Application application = selectionContext.getSelection("application");
		String url = editApplication(application);
		return url;
	}
	
	public String editApplication(Application application) {
		try {
			//application = clone(application);
			selectionContext.resetOrigin();
			selectionContext.setSelection("application", application);
			String url = applicationPageManager.initializeApplicationUpdatePage(application);
			applicationPageManager.pushContext(applicationWizard);
			initialize(application);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public void saveApplication() {
		Application application = getApplication();
		if (validateApplication(application)) {
			if (isImmediate())
				persistApplication(application);
			outject("application", application);
		}
	}
	
	public void persistApplication(Application application) {
		saveApplication(application);
	}
	
	public void saveApplication(Application application) {
		try {
			saveApplicationToSystem(application);
			applicationEventManager.fireAddedEvent(application);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveApplicationToSystem(Application application) {
		applicationDataManager.saveApplication(application);
	}
	
	public void handleSaveApplication(@Observes @Add Application application) {
		saveApplication(application);
	}
	
	public void addApplication(Application application) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichApplication(Application application) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Application application) {
		return validateApplication(application);
	}
	
	public boolean validateApplication(Application application) {
		Validator validator = getValidator();
		boolean isValid = ApplicationUtil.validate(application);
		Display display = getFromSession("display");
		display.setModule("applicationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveApplication() {
		display = getFromSession("display");
		display.setModule("applicationInfo");
		Application application = selectionContext.getSelection("application");
		if (application == null) {
			display.error("Application record must be selected.");
		}
	}
	
	public String handleRemoveApplication(@Observes @Remove Application application) {
		display = getFromSession("display");
		display.setModule("applicationInfo");
		try {
			display.info("Removing Application "+ApplicationUtil.getLabel(application)+" from the system.");
			removeApplicationFromSystem(application);
			selectionContext.clearSelection("application");
			applicationEventManager.fireClearSelectionEvent();
			applicationEventManager.fireRemovedEvent(application);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	protected void removeApplicationFromSystem(Application application) {
		if (applicationDataManager.removeApplication(application))
			setRecord(null);
	}
	
	public void cancelApplication() {
		BeanContext.removeFromSession("application");
		applicationPageManager.removeContext(applicationWizard);
	}

}
