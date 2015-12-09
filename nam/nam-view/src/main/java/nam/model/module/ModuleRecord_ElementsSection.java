package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.ui.AbstractWizardPage;

import nam.model.Module;
import nam.model.util.ModuleUtil;


@SessionScoped
@Named("moduleElementsSection")
public class ModuleRecord_ElementsSection extends AbstractWizardPage<Module> implements Serializable {

	private Module module;

	
	public ModuleRecord_ElementsSection() {
		setName("Elements");
		setUrl("elements");
	}

	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public String getIcon() {
		return "/icons/nam/Element16.gif";
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
