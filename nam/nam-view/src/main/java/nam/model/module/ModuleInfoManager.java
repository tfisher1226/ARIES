package nam.model.module;

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

import nam.model.Module;
import nam.model.Project;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("moduleInfoManager")
public class ModuleInfoManager extends AbstractNamRecordManager<Module> implements Serializable {
	
	@Inject
	private ModuleWizard moduleWizard;
	
	@Inject
	private ModuleDataManager moduleDataManager;
	
	@Inject
	private ModulePageManager modulePageManager;
	
	@Inject
	private ModuleEventManager moduleEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ModuleHelper moduleHelper;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	public ModuleInfoManager() {
		setInstanceName("module");
	}
	
	
	public Module getModule() {
		return getRecord();
	}
	
	public Module getSelectedModule() {
		return selectionContext.getSelection("module");
	}
	
	@Override
	public Class<Module> getRecordClass() {
		return Module.class;
	}
	
	@Override
	public boolean isEmpty(Module module) {
		return moduleHelper.isEmpty(module);
	}
	
	@Override
	public String toString(Module module) {
		return moduleHelper.toString(module);
	}
	
	@Override
	public void initialize() {
		Module module = selectionContext.getSelection("module");
		if (module != null)
			initialize(module);
	}
	
	protected void initialize(Module module) {
		moduleWizard.initialize(module);
		setContext("module", module);
	}
	
	@Override
	public String newRecord() {
		return newModule();
	}
	
	public String newModule() {
		try {
			Module module = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("module",  module);
			String url = modulePageManager.initializeModuleCreationPage(module);
			modulePageManager.pushContext(moduleWizard);
			initialize(module);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Module create() {
		Module module = ModuleUtil.create();
		return module;
	}
	
	@Override
	public Module clone(Module module) {
		module = ModuleUtil.clone(module);
		return module;
	}
	
	@Override
	public String viewRecord() {
		return viewModule();
	}
	
	public String viewModule() {
		Module module = selectionContext.getSelection("module");
		String url = viewModule(module);
		return url;
	}
	
	public String viewModule(Module module) {
		try {
			String url = modulePageManager.initializeModuleSummaryView(module);
			modulePageManager.pushContext(moduleWizard);
			initialize(module);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editModule();
	}
	
	public String editModule() {
		Module module = selectionContext.getSelection("module");
		String url = editModule(module);
		return url;
	}
	
	public String editModule(Module module) {
		try {
			//module = clone(module);
			selectionContext.resetOrigin();
			selectionContext.setSelection("module",  module);
			String url = modulePageManager.initializeModuleUpdatePage(module);
			modulePageManager.pushContext(moduleWizard);
			initialize(module);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveModule() {
		Module module = getModule();
		if (validateModule(module)) {
			saveModule(module);
		}
	}
	
	public void persistModule(Module module) {
			saveModule(module);
	}
	
	public void saveModule(Module module) {
		try {
			saveModuleToSystem(module);
			moduleEventManager.fireAddedEvent(module);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveModuleToSystem(Module module) {
		moduleDataManager.saveModule(module);
	}
	
	public void handleSaveModule(@Observes @Add Module module) {
		saveModule(module);
	}
	
	public void enrichModule(Module module) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Module module) {
		return validateModule(module);
	}
	
	public boolean validateModule(Module module) {
		Validator validator = getValidator();
		boolean isValid = ModuleUtil.validate(module);
		Display display = getFromSession("display");
		display.setModule("moduleInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveModule() {
		display = getFromSession("display");
		display.setModule("moduleInfo");
		Module module = selectionContext.getSelection("module");
		if (module == null) {
			display.error("Module record must be selected.");
		}
	}
	
	public String handleRemoveModule(@Observes @Remove Module module) {
		display = getFromSession("display");
		display.setModule("moduleInfo");
		try {
			display.info("Removing Module "+ModuleUtil.getLabel(module)+" from the system.");
			removeModuleFromSystem(module);
			selectionContext.clearSelection("module");
			moduleEventManager.fireClearSelectionEvent();
			moduleEventManager.fireRemovedEvent(module);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeModuleFromSystem(Module module) {
		if (moduleDataManager.removeModule(module))
			setRecord(null);
	}
	
	public void cancelModule() {
		BeanContext.removeFromSession("module");
		modulePageManager.removeContext(moduleWizard);
	}
	
}
