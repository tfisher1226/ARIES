package nam.model.process;

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
import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("processInfoManager")
public class ProcessInfoManager extends AbstractNamRecordManager<Process> implements Serializable {
	
	@Inject
	private ProcessWizard processWizard;
	
	@Inject
	private ProcessDataManager processDataManager;
	
	@Inject
	private ProcessPageManager processPageManager;
	
	@Inject
	private ProcessEventManager processEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ProcessHelper processHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ProcessInfoManager() {
		setInstanceName("process");
	}
	
	
	public Process getProcess() {
		return getRecord();
	}
	
	public Process getSelectedProcess() {
		return selectionContext.getSelection("process");
	}
	
	@Override
	public Class<Process> getRecordClass() {
		return Process.class;
	}
	
	@Override
	public boolean isEmpty(Process process) {
		return processHelper.isEmpty(process);
	}
	
	@Override
	public String toString(Process process) {
		return processHelper.toString(process);
	}
	
	@Override
	public void initialize() {
		Process process = selectionContext.getSelection("process");
		if (process != null)
			initialize(process);
	}
	
	protected void initialize(Process process) {
		ProcessUtil.initialize(process);
		processWizard.initialize(process);
		setContext("process", process);
	}
	
	public void handleProcessSelected(@Observes @Selected Process process) {
		selectionContext.setSelection("process",  process);
		processPageManager.updateState(process);
		processPageManager.refreshMembers();
		setRecord(process);
	}
	
	@Override
	public String newRecord() {
		return newProcess();
	}
	
	public String newProcess() {
		try {
			Process process = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("process",  process);
			String url = processPageManager.initializeProcessCreationPage(process);
			processPageManager.pushContext(processWizard);
			initialize(process);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Process create() {
		Process process = ProcessUtil.create();
		return process;
	}
	
	@Override
	public Process clone(Process process) {
		process = ProcessUtil.clone(process);
		return process;
	}
	
	@Override
	public String viewRecord() {
		return viewProcess();
	}
	
	public String viewProcess() {
		Process process = selectionContext.getSelection("process");
		String url = viewProcess(process);
		return url;
	}
	
	public String viewProcess(Process process) {
		try {
			String url = processPageManager.initializeProcessSummaryView(process);
			processPageManager.pushContext(processWizard);
			initialize(process);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editProcess();
	}
	
	public String editProcess() {
		Process process = selectionContext.getSelection("process");
		String url = editProcess(process);
		return url;
	}
	
	public String editProcess(Process process) {
		try {
			//process = clone(process);
			selectionContext.resetOrigin();
			selectionContext.setSelection("process",  process);
			String url = processPageManager.initializeProcessUpdatePage(process);
			processPageManager.pushContext(processWizard);
			initialize(process);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveProcess() {
		Process process = getProcess();
		if (validateProcess(process)) {
			if (isImmediate())
				persistProcess(process);
			outject("process", process);
		}
	}
	
	public void persistProcess(Process process) {
		saveProcess(process);
	}
	
	public void saveProcess(Process process) {
		try {
			saveProcessToSystem(process);
			processEventManager.fireAddedEvent(process);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveProcessToSystem(Process process) {
		processDataManager.saveProcess(process);
	}
	
	public void handleSaveProcess(@Observes @Add Process process) {
		saveProcess(process);
	}
	
	public void addProcess(Process process) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichProcess(Process process) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Process process) {
		return validateProcess(process);
	}
	
	public boolean validateProcess(Process process) {
		Validator validator = getValidator();
		boolean isValid = ProcessUtil.validate(process);
		Display display = getFromSession("display");
		display.setModule("processInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveProcess() {
		display = getFromSession("display");
		display.setModule("processInfo");
		Process process = selectionContext.getSelection("process");
		if (process == null) {
			display.error("Process record must be selected.");
		}
	}
	
	public String handleRemoveProcess(@Observes @Remove Process process) {
		display = getFromSession("display");
		display.setModule("processInfo");
		try {
			display.info("Removing Process "+ProcessUtil.getLabel(process)+" from the system.");
			removeProcessFromSystem(process);
			selectionContext.clearSelection("process");
			processEventManager.fireClearSelectionEvent();
			processEventManager.fireRemovedEvent(process);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeProcessFromSystem(Process process) {
		if (processDataManager.removeProcess(process))
			setRecord(null);
	}
	
	public void cancelProcess() {
		BeanContext.removeFromSession("process");
		processPageManager.removeContext(processWizard);
	}
	
}
