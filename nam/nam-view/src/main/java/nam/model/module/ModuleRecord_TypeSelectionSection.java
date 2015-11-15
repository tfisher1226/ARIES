package nam.model.module;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import nam.model.Module;
import nam.model.ModuleType;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;


@SessionScoped
@Named("moduleTypeSelectionSection")
public class ModuleRecord_TypeSelectionSection extends AbstractWizardPage<Module> {

	private Module module;

	private ModuleType moduleType;

	
	public ModuleRecord_TypeSelectionSection() {
		//setTitle("Specify desired configuration.");
		setName("Type Selection");
		setUrl("typeSelection");
		//setOwner(owner);
	}
	
	public ModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleType moduleType) {
		if (moduleType != null)
			this.moduleType = moduleType;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void initialize(Module module) {
		setEnabled(true);
		setBackVisible(false);
		setNextVisible(true);
		setNextEnabled(true);
		setFinishVisible(false);
		setModule(module);
	}
	
	@Override
	public String next() {
		ModuleInfoManager moduleInfoManager = BeanContext.getFromSession("moduleInfoManager");
		return moduleInfoManager.newModule();
	}
	
	public void validate() {
		if (moduleType == null) {
			validator.missing("Module Type");
		} else {
			//if (module.get
			//if (module.getConfiguration() == null)
			//	validator.missing("Configuration");
			//module.setType(moduleType);
			ModuleWizard moduleWizard = BeanContext.getFromSession("moduleWizard");
			moduleWizard.setModuleType(moduleType);

		}
	}
	
}
