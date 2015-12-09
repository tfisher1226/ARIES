package nam.model.parameter;

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
import org.aries.util.Validator;

import nam.model.Parameter;
import nam.model.Project;
import nam.model.util.ParameterUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("parameterInfoManager")
public class ParameterInfoManager extends AbstractNamRecordManager<Parameter> implements Serializable {
	
	@Inject
	private ParameterWizard parameterWizard;
	
	@Inject
	private ParameterDataManager parameterDataManager;
	
	@Inject
	private ParameterPageManager parameterPageManager;
	
	@Inject
	private ParameterEventManager parameterEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ParameterHelper parameterHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ParameterInfoManager() {
		setInstanceName("parameter");
	}
	
	
	public Parameter getParameter() {
		return getRecord();
	}
	
	public Parameter getSelectedParameter() {
		return selectionContext.getSelection("parameter");
	}
	
	@Override
	public Class<Parameter> getRecordClass() {
		return Parameter.class;
	}
	
	@Override
	public boolean isEmpty(Parameter parameter) {
		return parameterHelper.isEmpty(parameter);
	}
	
	@Override
	public String toString(Parameter parameter) {
		return parameterHelper.toString(parameter);
	}
	
	@Override
	public void initialize() {
		Parameter parameter = selectionContext.getSelection("parameter");
		if (parameter != null)
			initialize(parameter);
	}
	
	protected void initialize(Parameter parameter) {
		parameterWizard.initialize(parameter);
		setContext("parameter", parameter);
	}
	
	@Override
	public String newRecord() {
		return newParameter();
	}
	
	public String newParameter() {
		try {
			Parameter parameter = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("parameter",  parameter);
			String url = parameterPageManager.initializeParameterCreationPage(parameter);
			parameterPageManager.pushContext(parameterWizard);
			initialize(parameter);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Parameter create() {
		Parameter parameter = ParameterUtil.create();
		return parameter;
	}
	
	@Override
	public Parameter clone(Parameter parameter) {
		parameter = ParameterUtil.clone(parameter);
		return parameter;
	}
	
	@Override
	public String viewRecord() {
		return viewParameter();
	}
	
	public String viewParameter() {
		Parameter parameter = selectionContext.getSelection("parameter");
		String url = viewParameter(parameter);
		return url;
	}
	
	public String viewParameter(Parameter parameter) {
		try {
			String url = parameterPageManager.initializeParameterSummaryView(parameter);
			parameterPageManager.pushContext(parameterWizard);
			initialize(parameter);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editParameter();
	}
	
	public String editParameter() {
		Parameter parameter = selectionContext.getSelection("parameter");
		String url = editParameter(parameter);
		return url;
	}
	
	public String editParameter(Parameter parameter) {
		try {
			//parameter = clone(parameter);
			selectionContext.resetOrigin();
			selectionContext.setSelection("parameter",  parameter);
			String url = parameterPageManager.initializeParameterUpdatePage(parameter);
			parameterPageManager.pushContext(parameterWizard);
			initialize(parameter);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveParameter() {
		Parameter parameter = getParameter();
		if (validateParameter(parameter)) {
			saveParameter(parameter);
		}
	}
	
	public void persistParameter(Parameter parameter) {
		saveParameter(parameter);
	}
	
	public void saveParameter(Parameter parameter) {
		try {
			saveParameterToSystem(parameter);
			parameterEventManager.fireAddedEvent(parameter);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveParameterToSystem(Parameter parameter) {
		parameterDataManager.saveParameter(parameter);
	}
	
	public void handleSaveParameter(@Observes @Add Parameter parameter) {
		saveParameter(parameter);
	}
	
	public void enrichParameter(Parameter parameter) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Parameter parameter) {
		return validateParameter(parameter);
	}
	
	public boolean validateParameter(Parameter parameter) {
		Validator validator = getValidator();
		boolean isValid = ParameterUtil.validate(parameter);
		Display display = getFromSession("display");
		display.setModule("parameterInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveParameter() {
		display = getFromSession("display");
		display.setModule("parameterInfo");
		Parameter parameter = selectionContext.getSelection("parameter");
		if (parameter == null) {
			display.error("Parameter record must be selected.");
		}
	}
	
	public String handleRemoveParameter(@Observes @Remove Parameter parameter) {
		display = getFromSession("display");
		display.setModule("parameterInfo");
		try {
			display.info("Removing Parameter "+ParameterUtil.getLabel(parameter)+" from the system.");
			removeParameterFromSystem(parameter);
			selectionContext.clearSelection("parameter");
			parameterEventManager.fireClearSelectionEvent();
			parameterEventManager.fireRemovedEvent(parameter);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeParameterFromSystem(Parameter parameter) {
		if (parameterDataManager.removeParameter(parameter))
			setRecord(null);
	}
	
	public void cancelParameter() {
		BeanContext.removeFromSession("parameter");
		parameterPageManager.removeContext(parameterWizard);
	}
	
}
