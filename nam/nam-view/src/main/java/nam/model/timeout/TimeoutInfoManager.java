package nam.model.timeout;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.Timeout;
import nam.model.util.ProjectUtil;
import nam.model.util.TimeoutUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("timeoutInfoManager")
public class TimeoutInfoManager extends AbstractNamRecordManager<Timeout> implements Serializable {
	
	@Inject
	private TimeoutWizard timeoutWizard;
	
	@Inject
	private TimeoutDataManager timeoutDataManager;
	
	@Inject
	private TimeoutPageManager timeoutPageManager;
	
	@Inject
	private TimeoutEventManager timeoutEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public TimeoutInfoManager() {
		setInstanceName("timeout");
	}
	
	
	public Timeout getTimeout() {
		return getRecord();
	}
	
	public Timeout getSelectedTimeout() {
		return selectionContext.getSelection("timeout");
	}
	
	@Override
	public Class<Timeout> getRecordClass() {
		return Timeout.class;
	}
	
	@Override
	public boolean isEmpty(Timeout timeout) {
		return getTimeoutHelper().isEmpty(timeout);
	}
	
	@Override
	public String toString(Timeout timeout) {
		return getTimeoutHelper().toString(timeout);
	}
	
	protected TimeoutHelper getTimeoutHelper() {
		return BeanContext.getFromSession("timeoutHelper");
	}
	
	@Override
	public void initialize() {
		Timeout timeout = selectionContext.getSelection("timeout");
		if (timeout != null)
			initialize(timeout);
	}
	
	protected void initialize(Timeout timeout) {
		TimeoutUtil.initialize(timeout);
		timeoutWizard.initialize(timeout);
		initializeOutjectedState(timeout);
		setContext("timeout", timeout);
	}
	
	protected void initializeOutjectedState(Timeout timeout) {
		outject("timeout", timeout);
	}
	
	public void handleTimeoutSelected(@Observes @Selected Timeout timeout) {
		selectionContext.setSelection("timeout",  timeout);
		timeoutPageManager.updateState(timeout);
		setRecord(timeout);
	}
	
	@Override
	public String newRecord() {
		return newTimeout();
	}
	
	public String newTimeout() {
		try {
			Timeout timeout = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("timeout",  timeout);
			String url = timeoutPageManager.initializeTimeoutCreationPage(timeout);
			timeoutPageManager.pushContext(timeoutWizard);
			initialize(timeout);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Timeout create() {
		Timeout timeout = TimeoutUtil.create();
		return timeout;
	}
	
	@Override
	public Timeout clone(Timeout timeout) {
		timeout = TimeoutUtil.clone(timeout);
		return timeout;
	}
	
	@Override
	public String viewRecord() {
		return viewTimeout();
	}
	
	public String viewTimeout() {
		Timeout timeout = selectionContext.getSelection("timeout");
		String url = viewTimeout(timeout);
		return url;
	}
	
	public String viewTimeout(Timeout timeout) {
		try {
			String url = timeoutPageManager.initializeTimeoutSummaryView(timeout);
			timeoutPageManager.pushContext(timeoutWizard);
			initialize(timeout);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editTimeout();
	}
	
	public String editTimeout() {
		Timeout timeout = selectionContext.getSelection("timeout");
		String url = editTimeout(timeout);
		return url;
	}
	
	public String editTimeout(Timeout timeout) {
		try {
			//timeout = clone(timeout);
			selectionContext.resetOrigin();
			selectionContext.setSelection("timeout",  timeout);
			String url = timeoutPageManager.initializeTimeoutUpdatePage(timeout);
			timeoutPageManager.pushContext(timeoutWizard);
			initialize(timeout);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveTimeout() {
		Timeout timeout = getTimeout();
		if (validateTimeout(timeout)) {
			saveTimeout(timeout);
		}
	}
	
	public void persistTimeout(Timeout timeout) {
		saveTimeout(timeout);
	}
	
	public void saveTimeout(Timeout timeout) {
		try {
			saveTimeoutToSystem(timeout);
			timeoutEventManager.fireAddedEvent(timeout);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveTimeoutToSystem(Timeout timeout) {
		timeoutDataManager.saveTimeout(timeout);
	}
	
	public void handleSaveTimeout(@Observes @Add Timeout timeout) {
		saveTimeout(timeout);
	}
	
	public void enrichTimeout(Timeout timeout) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Timeout timeout) {
		return validateTimeout(timeout);
	}
	
	public boolean validateTimeout(Timeout timeout) {
		Validator validator = getValidator();
		boolean isValid = TimeoutUtil.validate(timeout);
		Display display = getFromSession("display");
		display.setModule("timeoutInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveTimeout() {
		display = getFromSession("display");
		display.setModule("timeoutInfo");
		Timeout timeout = selectionContext.getSelection("timeout");
		if (timeout == null) {
			display.error("Timeout record must be selected.");
		}
	}
	
	public String handleRemoveTimeout(@Observes @Remove Timeout timeout) {
		display = getFromSession("display");
		display.setModule("timeoutInfo");
		try {
			display.info("Removing Timeout "+TimeoutUtil.getLabel(timeout)+" from the system.");
			removeTimeoutFromSystem(timeout);
			selectionContext.clearSelection("timeout");
			timeoutEventManager.fireClearSelectionEvent();
			timeoutEventManager.fireRemovedEvent(timeout);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeTimeoutFromSystem(Timeout timeout) {
		if (timeoutDataManager.removeTimeout(timeout))
			setRecord(null);
	}
	
	public void cancelTimeout() {
		BeanContext.removeFromSession("timeout");
		timeoutPageManager.removeContext(timeoutWizard);
	}
	
}
