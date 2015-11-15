package nam.model.module;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;

import nam.model.Module;
import nam.model.Configuration;


@SessionScoped
@Named("moduleSourceSelectionSection")
public class ModuleRecord_SourceSelectionSection extends AbstractWizardPage<Module> {

	//private static Log log = LogFactory.getLog(ModuleConfigPage.class);

	private Module module;

	
	public ModuleRecord_SourceSelectionSection() {
		//setTitle("Specify desired configuration.");
		setName("Source Selection");
		setUrl("sourceSelection");
		//setOwner(owner);
	}
	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void initialize(Module module) {
		setBackVisible(false);
		setNextVisible(false);
		setFinishVisible(false);
		setModule(module);
	}
	
	@Override
	public String next() {
		ModulePageManager modulePageManager = BeanContext.getFromSession("modulePageManager");
		return modulePageManager.initializeModuleTypeSelectionPage();
	}
	
	public void validate() {
		if (module == null) {
			validator.missing("Module");
		} else {
			//if (module.get
			//if (module.getConfiguration() == null)
			//	validator.missing("Configuration");
		}
	}
	
}
