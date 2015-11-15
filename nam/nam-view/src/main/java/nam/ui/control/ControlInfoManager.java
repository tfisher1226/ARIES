package nam.ui.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import nam.ui.Control;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;
import nam.ui.util.ControlUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.ui.event.Selected;
import org.aries.util.Validator;


@SessionScoped
@Named("controlInfoManager")
public class ControlInfoManager extends AbstractNamRecordManager<Control> implements Serializable {
	
	@Inject
	private ControlWizard controlWizard;
	
	@Inject
	private ControlDataManager controlDataManager;
	
	@Inject
	private ControlPageManager controlPageManager;
	
	@Inject
	private ControlEventManager controlEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ControlHelper controlHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ControlInfoManager() {
		setInstanceName("control");
	}
	
	
	public Control getControl() {
		return getRecord();
	}
	
	public Control getSelectedControl() {
		return selectionContext.getSelection("control");
	}
	
	@Override
	public Class<Control> getRecordClass() {
		return Control.class;
	}
	
	@Override
	public boolean isEmpty(Control control) {
		return controlHelper.isEmpty(control);
	}
	
	@Override
	public String toString(Control control) {
		return controlHelper.toString(control);
	}
	
	protected ControlHelper getControlHelper() {
		return BeanContext.getFromSession("controlHelper");
	}
	
	@Override
	public void initialize() {
		Control control = selectionContext.getSelection("control");
		if (control != null)
			initialize(control);
	}
	
	protected void initialize(Control control) {
		ControlUtil.initialize(control);
		controlWizard.initialize(control);
		setContext("control", control);
	}
	
	public void handleControlSelected(@Observes @Selected Control control) {
		selectionContext.setSelection("control",  control);
		controlPageManager.updateState(control);
		controlPageManager.refreshMembers();
		setRecord(control);
	}
	
	@Override
	public String newRecord() {
		return newControl();
	}
	
	public String newControl() {
		try {
			Control control = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("control",  control);
			String url = controlPageManager.initializeControlCreationPage(control);
			controlPageManager.pushContext(controlWizard);
			initialize(control);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Control create() {
		Control control = ControlUtil.create();
		return control;
	}
	
	@Override
	public Control clone(Control control) {
		control = ControlUtil.clone(control);
		return control;
	}
	
	@Override
	public String viewRecord() {
		return viewControl();
	}
	
	public String viewControl() {
		Control control = selectionContext.getSelection("control");
		String url = viewControl(control);
		return url;
	}
	
	public String viewControl(Control control) {
		try {
			String url = controlPageManager.initializeControlSummaryView(control);
			controlPageManager.pushContext(controlWizard);
			initialize(control);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editControl();
	}
	
	public String editControl() {
		Control control = selectionContext.getSelection("control");
		String url = editControl(control);
		return url;
	}
	
	public String editControl(Control control) {
		try {
			//control = clone(control);
			selectionContext.resetOrigin();
			selectionContext.setSelection("control",  control);
			String url = controlPageManager.initializeControlUpdatePage(control);
			controlPageManager.pushContext(controlWizard);
			initialize(control);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveControl() {
		Control control = getControl();
		if (validateControl(control)) {
			if (isImmediate())
				persistControl(control);
			outject("control", control);
		}
	}
	
	public void persistControl(Control control) {
		saveControl(control);
	}
	
	public void saveControl(Control control) {
		try {
			saveControlToSystem(control);
			controlEventManager.fireAddedEvent(control);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveControlToSystem(Control control) {
		controlDataManager.saveControl(control);
	}
	
	public void handleSaveControl(@Observes @Add Control control) {
		saveControl(control);
	}
	
	public void addControl(Control control) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichControl(Control control) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Control control) {
		return validateControl(control);
	}
	
	public boolean validateControl(Control control) {
		Validator validator = getValidator();
		boolean isValid = ControlUtil.validate(control);
		Display display = getFromSession("display");
		display.setModule("controlInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveControl() {
		display = getFromSession("display");
		display.setModule("controlInfo");
		Control control = selectionContext.getSelection("control");
		if (control == null) {
			display.error("Control record must be selected.");
		}
	}
	
	public String handleRemoveControl(@Observes @Remove Control control) {
		display = getFromSession("display");
		display.setModule("controlInfo");
		try {
			display.info("Removing Control "+ControlUtil.getLabel(control)+" from the system.");
			removeControlFromSystem(control);
			selectionContext.clearSelection("control");
			controlEventManager.fireClearSelectionEvent();
			controlEventManager.fireRemovedEvent(control);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeControlFromSystem(Control control) {
		if (controlDataManager.removeControl(control))
			setRecord(null);
	}
	
	public void cancelControl() {
		BeanContext.removeFromSession("control");
		controlPageManager.removeContext(controlWizard);
	}
	
}
