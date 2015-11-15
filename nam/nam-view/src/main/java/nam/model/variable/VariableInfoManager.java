package nam.model.variable;

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
import nam.model.Variable;
import nam.model.util.ProjectUtil;
import nam.model.util.VariableUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("variableInfoManager")
public class VariableInfoManager extends AbstractNamRecordManager<Variable> implements Serializable {
	
	@Inject
	private VariableWizard variableWizard;
	
	@Inject
	private VariableDataManager variableDataManager;
	
	@Inject
	private VariablePageManager variablePageManager;
	
	@Inject
	private VariableEventManager variableEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public VariableInfoManager() {
		setInstanceName("variable");
	}
	
	
	public Variable getVariable() {
		return getRecord();
	}
	
	public Variable getSelectedVariable() {
		return selectionContext.getSelection("variable");
	}
	
	@Override
	public Class<Variable> getRecordClass() {
		return Variable.class;
	}
	
	@Override
	public boolean isEmpty(Variable variable) {
		return getVariableHelper().isEmpty(variable);
	}
	
	@Override
	public String toString(Variable variable) {
		return getVariableHelper().toString(variable);
	}
	
	protected VariableHelper getVariableHelper() {
		return BeanContext.getFromSession("variableHelper");
	}
	
	@Override
	public void initialize() {
		Variable variable = selectionContext.getSelection("variable");
		if (variable != null)
			initialize(variable);
	}
	
	protected void initialize(Variable variable) {
		VariableUtil.initialize(variable);
		variableWizard.initialize(variable);
		setContext("variable", variable);
	}
	
	public void handleVariableSelected(@Observes @Selected Variable variable) {
		selectionContext.setSelection("variable",  variable);
		variablePageManager.updateState(variable);
		variablePageManager.refreshMembers();
		setRecord(variable);
	}
	
	@Override
	public String newRecord() {
		return newVariable();
	}
	
	public String newVariable() {
		try {
			Variable variable = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("variable",  variable);
			String url = variablePageManager.initializeVariableCreationPage(variable);
			variablePageManager.pushContext(variableWizard);
			initialize(variable);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Variable create() {
		Variable variable = VariableUtil.create();
		return variable;
	}
	
	@Override
	public Variable clone(Variable variable) {
		variable = VariableUtil.clone(variable);
		return variable;
	}
	
	@Override
	public String viewRecord() {
		return viewVariable();
	}
	
	public String viewVariable() {
		Variable variable = selectionContext.getSelection("variable");
		String url = viewVariable(variable);
		return url;
	}
	
	public String viewVariable(Variable variable) {
		try {
			String url = variablePageManager.initializeVariableSummaryView(variable);
			variablePageManager.pushContext(variableWizard);
			initialize(variable);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editVariable();
	}
	
	public String editVariable() {
		Variable variable = selectionContext.getSelection("variable");
		String url = editVariable(variable);
		return url;
	}
	
	public String editVariable(Variable variable) {
		try {
			//variable = clone(variable);
			selectionContext.resetOrigin();
			selectionContext.setSelection("variable",  variable);
			String url = variablePageManager.initializeVariableUpdatePage(variable);
			variablePageManager.pushContext(variableWizard);
			initialize(variable);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveVariable() {
		Variable variable = getVariable();
		if (validateVariable(variable)) {
			saveVariable(variable);
		}
	}
	
	public void persistVariable(Variable variable) {
		saveVariable(variable);
	}
	
	public void saveVariable(Variable variable) {
		try {
			saveVariableToSystem(variable);
			variableEventManager.fireAddedEvent(variable);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveVariableToSystem(Variable variable) {
		variableDataManager.saveVariable(variable);
	}
	
	public void handleSaveVariable(@Observes @Add Variable variable) {
		saveVariable(variable);
	}
	
	public void enrichVariable(Variable variable) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Variable variable) {
		return validateVariable(variable);
	}
	
	public boolean validateVariable(Variable variable) {
		Validator validator = getValidator();
		boolean isValid = VariableUtil.validate(variable);
		Display display = getFromSession("display");
		display.setModule("variableInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveVariable() {
		display = getFromSession("display");
		display.setModule("variableInfo");
		Variable variable = selectionContext.getSelection("variable");
		if (variable == null) {
			display.error("Variable record must be selected.");
		}
	}
	
	public String handleRemoveVariable(@Observes @Remove Variable variable) {
		display = getFromSession("display");
		display.setModule("variableInfo");
		try {
			display.info("Removing Variable "+VariableUtil.getLabel(variable)+" from the system.");
			removeVariableFromSystem(variable);
			selectionContext.clearSelection("variable");
			variableEventManager.fireClearSelectionEvent();
			variableEventManager.fireRemovedEvent(variable);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeVariableFromSystem(Variable variable) {
		if (variableDataManager.removeVariable(variable))
			setRecord(null);
	}
	
	public void cancelVariable() {
		BeanContext.removeFromSession("variable");
		variablePageManager.removeContext(variableWizard);
	}
	
}
