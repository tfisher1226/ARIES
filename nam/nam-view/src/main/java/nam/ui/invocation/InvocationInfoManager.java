package nam.ui.invocation;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Service;
import nam.model.service.ServiceSelectManager;
import nam.ui.Invocation;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.util.InvocationUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("invocationInfoManager")
public class InvocationInfoManager extends AbstractNamRecordManager<Invocation> implements Serializable {
	
	@Inject
	private InvocationWizard invocationWizard;
	
	@Inject
	private InvocationDataManager invocationDataManager;
	
	@Inject
	private InvocationPageManager invocationPageManager;
	
	@Inject
	private InvocationEventManager invocationEventManager;
	
	@Inject
	private ServiceSelectManager serviceSelectManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private InvocationHelper invocationHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public InvocationInfoManager() {
		setInstanceName("invocation");
	}
	
	
	public Invocation getInvocation() {
		return getRecord();
	}
	
	public Invocation getSelectedInvocation() {
		return selectionContext.getSelection("invocation");
	}
	
	@Override
	public Class<Invocation> getRecordClass() {
		return Invocation.class;
	}
	
	@Override
	public boolean isEmpty(Invocation invocation) {
		return invocationHelper.isEmpty(invocation);
	}
	
	@Override
	public String toString(Invocation invocation) {
		return invocationHelper.toString(invocation);
	}
	
	protected InvocationHelper getInvocationHelper() {
		return BeanContext.getFromSession("invocationHelper");
	}
	
	@Override
	public void initialize() {
		Invocation invocation = selectionContext.getSelection("invocation");
		if (invocation != null)
			initialize(invocation);
	}
	
	protected void initialize(Invocation invocation) {
		InvocationUtil.initialize(invocation);
		invocationWizard.initialize(invocation);
		setContext("invocation", invocation);
	}
	
	public void handleInvocationServiceSelected(@Observes @Selected Service service) {
		getInvocation().setService(service);
	}
	
	public void handleInvocationSelected(@Observes @Selected Invocation invocation) {
		selectionContext.setSelection("invocation",  invocation);
		invocationPageManager.updateState(invocation);
		invocationPageManager.refreshMembers();
		setRecord(invocation);
	}
	
	@Override
	public String newRecord() {
		return newInvocation();
	}
	
	public String newInvocation() {
		try {
			Invocation invocation = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("invocation",  invocation);
			String url = invocationPageManager.initializeInvocationCreationPage(invocation);
			invocationPageManager.pushContext(invocationWizard);
			initialize(invocation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Invocation create() {
		Invocation invocation = InvocationUtil.create();
		return invocation;
	}
	
	@Override
	public Invocation clone(Invocation invocation) {
		invocation = InvocationUtil.clone(invocation);
		return invocation;
	}
	
	@Override
	public String viewRecord() {
		return viewInvocation();
	}
	
	public String viewInvocation() {
		Invocation invocation = selectionContext.getSelection("invocation");
		String url = viewInvocation(invocation);
		return url;
	}
	
	public String viewInvocation(Invocation invocation) {
		try {
			String url = invocationPageManager.initializeInvocationSummaryView(invocation);
			invocationPageManager.pushContext(invocationWizard);
			initialize(invocation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editInvocation();
	}
	
	public String editInvocation() {
		Invocation invocation = selectionContext.getSelection("invocation");
		String url = editInvocation(invocation);
		return url;
	}
	
	public String editInvocation(Invocation invocation) {
		try {
			//invocation = clone(invocation);
			selectionContext.resetOrigin();
			selectionContext.setSelection("invocation",  invocation);
			String url = invocationPageManager.initializeInvocationUpdatePage(invocation);
			invocationPageManager.pushContext(invocationWizard);
			initialize(invocation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveInvocation() {
		Invocation invocation = getInvocation();
		if (validateInvocation(invocation)) {
			if (isImmediate())
				persistInvocation(invocation);
			outject("invocation", invocation);
		}
	}
	
	public void persistInvocation(Invocation invocation) {
		saveInvocation(invocation);
	}
	
	public void saveInvocation(Invocation invocation) {
		try {
			saveInvocationToSystem(invocation);
			invocationEventManager.fireAddedEvent(invocation);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveInvocationToSystem(Invocation invocation) {
		invocationDataManager.saveInvocation(invocation);
	}
	
	public void handleSaveInvocation(@Observes @Add Invocation invocation) {
		saveInvocation(invocation);
	}
	
	public void addInvocation(Invocation invocation) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichInvocation(Invocation invocation) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Invocation invocation) {
		return validateInvocation(invocation);
	}
	
	public boolean validateInvocation(Invocation invocation) {
		Validator validator = getValidator();
		boolean isValid = InvocationUtil.validate(invocation);
		Display display = getFromSession("display");
		display.setModule("invocationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveInvocation() {
		display = getFromSession("display");
		display.setModule("invocationInfo");
		Invocation invocation = selectionContext.getSelection("invocation");
		if (invocation == null) {
			display.error("Invocation record must be selected.");
		}
	}
	
	public String handleRemoveInvocation(@Observes @Remove Invocation invocation) {
		display = getFromSession("display");
		display.setModule("invocationInfo");
		try {
			display.info("Removing Invocation "+InvocationUtil.getLabel(invocation)+" from the system.");
			removeInvocationFromSystem(invocation);
			selectionContext.clearSelection("invocation");
			invocationEventManager.fireClearSelectionEvent();
			invocationEventManager.fireRemovedEvent(invocation);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeInvocationFromSystem(Invocation invocation) {
		if (invocationDataManager.removeInvocation(invocation))
			setRecord(null);
	}
	
	public void cancelInvocation() {
		BeanContext.removeFromSession("invocation");
		invocationPageManager.removeContext(invocationWizard);
	}
	
}
