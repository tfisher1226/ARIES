package nam.model.operation;

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

import nam.model.Operation;
import nam.model.Project;
import nam.model.util.OperationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("operationInfoManager")
public class OperationInfoManager extends AbstractNamRecordManager<Operation> implements Serializable {
	
	@Inject
	private OperationWizard operationWizard;
	
	@Inject
	private OperationDataManager operationDataManager;
	
	@Inject
	private OperationPageManager operationPageManager;
	
	@Inject
	private OperationEventManager operationEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private OperationHelper operationHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public OperationInfoManager() {
		setInstanceName("operation");
	}
	
	
	public Operation getOperation() {
		return getRecord();
	}
	
	public Operation getSelectedOperation() {
		return selectionContext.getSelection("operation");
	}
	
	@Override
	public Class<Operation> getRecordClass() {
		return Operation.class;
	}
	
	@Override
	public boolean isEmpty(Operation operation) {
		return operationHelper.isEmpty(operation);
	}
	
	@Override
	public String toString(Operation operation) {
		return operationHelper.toString(operation);
	}
	
	@Override
	public void initialize() {
		Operation operation = selectionContext.getSelection("operation");
		if (operation != null)
			initialize(operation);
	}
	
	protected void initialize(Operation operation) {
		OperationUtil.initialize(operation);
		operationWizard.initialize(operation);
		setContext("operation", operation);
	}
	
	public void handleOperationSelected(@Observes @Selected Operation operation) {
		selectionContext.setSelection("operation",  operation);
		operationPageManager.updateState(operation);
		operationPageManager.refreshMembers();
		setRecord(operation);
	}
	
	@Override
	public String newRecord() {
		return newOperation();
	}
	
	public String newOperation() {
		try {
			Operation operation = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("operation",  operation);
			String url = operationPageManager.initializeOperationCreationPage(operation);
			operationPageManager.pushContext(operationWizard);
			initialize(operation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Operation create() {
		Operation operation = OperationUtil.create();
		return operation;
	}
	
	@Override
	public Operation clone(Operation operation) {
		operation = OperationUtil.clone(operation);
		return operation;
	}
	
	@Override
	public String viewRecord() {
		return viewOperation();
	}
	
	public String viewOperation() {
		Operation operation = selectionContext.getSelection("operation");
		String url = viewOperation(operation);
		return url;
	}
	
	public String viewOperation(Operation operation) {
		try {
			String url = operationPageManager.initializeOperationSummaryView(operation);
			operationPageManager.pushContext(operationWizard);
			initialize(operation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editOperation();
	}
	
	public String editOperation() {
		Operation operation = selectionContext.getSelection("operation");
		String url = editOperation(operation);
		return url;
	}
	
	public String editOperation(Operation operation) {
		try {
			//operation = clone(operation);
			selectionContext.resetOrigin();
			selectionContext.setSelection("operation",  operation);
			String url = operationPageManager.initializeOperationUpdatePage(operation);
			operationPageManager.pushContext(operationWizard);
			initialize(operation);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveOperation() {
		Operation operation = getOperation();
		if (validateOperation(operation)) {
			saveOperation(operation);
		}
	}
	
	public void persistOperation(Operation operation) {
		saveOperation(operation);
	}
	
	public void saveOperation(Operation operation) {
		try {
			saveOperationToSystem(operation);
			operationEventManager.fireAddedEvent(operation);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveOperationToSystem(Operation operation) {
		operationDataManager.saveOperation(operation);
	}
	
	public void handleSaveOperation(@Observes @Add Operation operation) {
		saveOperation(operation);
	}
	
	public void enrichOperation(Operation operation) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Operation operation) {
		return validateOperation(operation);
	}
	
	public boolean validateOperation(Operation operation) {
		Validator validator = getValidator();
		boolean isValid = OperationUtil.validate(operation);
		Display display = getFromSession("display");
		display.setModule("operationInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveOperation() {
		display = getFromSession("display");
		display.setModule("operationInfo");
		Operation operation = selectionContext.getSelection("operation");
		if (operation == null) {
			display.error("Operation record must be selected.");
		}
	}
	
	public String handleRemoveOperation(@Observes @Remove Operation operation) {
		display = getFromSession("display");
		display.setModule("operationInfo");
		try {
			display.info("Removing Operation "+OperationUtil.getLabel(operation)+" from the system.");
			removeOperationFromSystem(operation);
			selectionContext.clearSelection("operation");
			operationEventManager.fireClearSelectionEvent();
			operationEventManager.fireRemovedEvent(operation);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeOperationFromSystem(Operation operation) {
		if (operationDataManager.removeOperation(operation))
			setRecord(null);
	}
	
	public void cancelOperation() {
		BeanContext.removeFromSession("operation");
		operationPageManager.removeContext(operationWizard);
	}
	
}
