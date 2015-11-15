package nam.model.fault;

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

import nam.model.Fault;
import nam.model.Project;
import nam.model.util.FaultUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("faultInfoManager")
public class FaultInfoManager extends AbstractNamRecordManager<Fault> implements Serializable {
	
	@Inject
	private FaultWizard faultWizard;
	
	@Inject
	private FaultDataManager faultDataManager;
	
	@Inject
	private FaultPageManager faultPageManager;
	
	@Inject
	private FaultEventManager faultEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private FaultHelper faultHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public FaultInfoManager() {
		setInstanceName("fault");
	}
	
	
	public Fault getFault() {
		return getRecord();
	}
	
	public Fault getSelectedFault() {
		return selectionContext.getSelection("fault");
	}
	
	@Override
	public Class<Fault> getRecordClass() {
		return Fault.class;
	}
	
	@Override
	public boolean isEmpty(Fault fault) {
		return faultHelper.isEmpty(fault);
	}
	
	@Override
	public String toString(Fault fault) {
		return faultHelper.toString(fault);
	}
	
	@Override
	public void initialize() {
		Fault fault = selectionContext.getSelection("fault");
		if (fault != null)
			initialize(fault);
	}
	
	protected void initialize(Fault fault) {
		FaultUtil.initialize(fault);
		faultWizard.initialize(fault);
		setContext("fault", fault);
	}
	
	public void handleFaultSelected(@Observes @Selected Fault fault) {
		selectionContext.setSelection("fault",  fault);
		faultPageManager.updateState(fault);
		faultPageManager.refreshMembers();
		setRecord(fault);
	}
	
	@Override
	public String newRecord() {
		return newFault();
	}
	
	public String newFault() {
		try {
			Fault fault = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("fault",  fault);
			String url = faultPageManager.initializeFaultCreationPage(fault);
			faultPageManager.pushContext(faultWizard);
			initialize(fault);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Fault create() {
		Fault fault = FaultUtil.create();
		return fault;
	}
	
	@Override
	public Fault clone(Fault fault) {
		fault = FaultUtil.clone(fault);
		return fault;
	}
	
	@Override
	public String viewRecord() {
		return viewFault();
	}
	
	public String viewFault() {
		Fault fault = selectionContext.getSelection("fault");
		String url = viewFault(fault);
		return url;
	}
	
	public String viewFault(Fault fault) {
		try {
			String url = faultPageManager.initializeFaultSummaryView(fault);
			faultPageManager.pushContext(faultWizard);
			initialize(fault);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editFault();
	}
	
	public String editFault() {
		Fault fault = selectionContext.getSelection("fault");
		String url = editFault(fault);
		return url;
	}
	
	public String editFault(Fault fault) {
		try {
			//fault = clone(fault);
			selectionContext.resetOrigin();
			selectionContext.setSelection("fault",  fault);
			String url = faultPageManager.initializeFaultUpdatePage(fault);
			faultPageManager.pushContext(faultWizard);
			initialize(fault);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveFault() {
		Fault fault = getFault();
		if (validateFault(fault)) {
			saveFault(fault);
		}
	}
	
	public void persistFault(Fault fault) {
		saveFault(fault);
	}
	
	public void saveFault(Fault fault) {
		try {
			saveFaultToSystem(fault);
			faultEventManager.fireAddedEvent(fault);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveFaultToSystem(Fault fault) {
		faultDataManager.saveFault(fault);
	}
	
	public void handleSaveFault(@Observes @Add Fault fault) {
		saveFault(fault);
	}
	
	public void enrichFault(Fault fault) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Fault fault) {
		return validateFault(fault);
	}
	
	public boolean validateFault(Fault fault) {
		Validator validator = getValidator();
		boolean isValid = FaultUtil.validate(fault);
		Display display = getFromSession("display");
		display.setModule("faultInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveFault() {
		display = getFromSession("display");
		display.setModule("faultInfo");
		Fault fault = selectionContext.getSelection("fault");
		if (fault == null) {
			display.error("Fault record must be selected.");
		}
	}
	
	public String handleRemoveFault(@Observes @Remove Fault fault) {
		display = getFromSession("display");
		display.setModule("faultInfo");
		try {
			display.info("Removing Fault "+FaultUtil.getLabel(fault)+" from the system.");
			removeFaultFromSystem(fault);
			selectionContext.clearSelection("fault");
			faultEventManager.fireClearSelectionEvent();
			faultEventManager.fireRemovedEvent(fault);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeFaultFromSystem(Fault fault) {
		if (faultDataManager.removeFault(fault))
			setRecord(null);
	}
	
	public void cancelFault() {
		BeanContext.removeFromSession("fault");
		faultPageManager.removeContext(faultWizard);
	}
	
}
