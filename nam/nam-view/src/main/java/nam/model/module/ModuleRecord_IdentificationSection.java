package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractWizardPage;

import nam.model.Module;
import nam.model.util.ModuleUtil;


@SessionScoped
@Named("moduleIdentificationSection")
public class ModuleRecord_IdentificationSection extends AbstractWizardPage<Module> implements Serializable {
	
	private Module module;

	
	public ModuleRecord_IdentificationSection() {
		setName("Identification");
		setUrl("identification");
	}

	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public void initialize(Module module) {
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		setPopulateVisible(true);
		setPopulateEnabled(true);
		setModule(module);
	}
	
	@Override
	public String back() {
		ModulePageManager modulePageManager = BeanContext.getFromSession("modulePageManager");
		return modulePageManager.initializeModuleTypeSelectionPage();
	}
	
	public void validate() {
		if (module == null) {
			validator.missing("Module");
		} else {
			if (StringUtils.isEmpty(module.getName()))
				validator.missing("Name");
			if (StringUtils.isEmpty(module.getGroupId()))
				validator.missing("Group ID");
			if (StringUtils.isEmpty(module.getArtifactId()))
				validator.missing("Artifact ID");
			if (StringUtils.isEmpty(module.getNamespace()))
				validator.missing("Namespace");
			if (StringUtils.isEmpty(module.getVersion()))
				validator.missing("Version");
		}
	}

}
