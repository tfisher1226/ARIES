package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Module;
import nam.model.util.ModuleUtil;


@SessionScoped
@Named("moduleOverviewSection")
public class ModuleRecord_OverviewSection extends AbstractWizardPage<Module> implements Serializable {
	
	private Module module;
	
	
	public ModuleRecord_OverviewSection() {
		setName("Overview");
		setUrl("overview");
	}
	
	
	public Module getModule() {
		return module;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
	
	@Override
	public void initialize(Module module) {
		setModule(module);
		setEnabled(true);
		setBackEnabled(false);
		setNextEnabled(true);
		setFinishEnabled(false);
		super.initialize(module);
	}
	
	@Override
	public void validate() {
		if (module == null) {
			validator.missing("Module");
		} else {
		}
	}
	
}
