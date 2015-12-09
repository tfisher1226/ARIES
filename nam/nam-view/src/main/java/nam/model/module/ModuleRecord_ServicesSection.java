package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Module;
import nam.model.util.ModuleUtil;


@SessionScoped
@Named("moduleServicesSection")
public class ModuleRecord_ServicesSection extends AbstractWizardPage<Module> implements Serializable {

	private Module module;

	
	public ModuleRecord_ServicesSection() {
		setName("Services");
		setUrl("services");
	}

	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public String getIcon() {
		return "/icons/nam/Service16.gif";
	}
	
	@Override
	public void initialize(Module module) {
		setEnabled(true);
		setBackEnabled(true);
		setNextEnabled(true);
		setFinishEnabled(false);
		setModule(module);
	}
	
	@Override
	public void validate() {
		if (module == null) {
			validator.missing("Module");
		} else {
		}
	}

}
