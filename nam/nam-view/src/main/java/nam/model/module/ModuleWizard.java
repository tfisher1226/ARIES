package nam.model.module;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import nam.model.Module;
import nam.model.ModuleType;
import nam.model.Project;
import nam.model.util.ModuleUtil;
import nam.ui.design.AbstractDomainElementWizard;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("moduleWizard")
@SuppressWarnings("serial")
public class ModuleWizard extends AbstractDomainElementWizard<Module> implements Serializable {

	@Inject
	private ModuleDataManager moduleDataManager;
	
	@Inject
	private ModulePageManager modulePageManager;

	@Inject
	private ModuleEventManager moduleEventManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	private ModuleType moduleType;

	
	@Override
	public String getName() {
		return "Module";
	}
	
	@Override
	public String getUrlContext() {
		return modulePageManager.getModuleWizardPage();
	}

	public ModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleType moduleType) {
		if (moduleType != null)
			this.moduleType = moduleType;
	}

	@Override
	public void initialize(Module module) {
		setOrigin(getSelectionContext().getUrl());
		assignPages(modulePageManager.getSections());
		super.initialize(module);
	}
	
	@Override
	public boolean isBackEnabled() {
		return super.isBackEnabled();
	}
	
	@Override
	public boolean isNextEnabled() {
		return super.isNextEnabled();
	}
	
	@Override
	public boolean isFinishEnabled() {
		return super.isFinishEnabled();
	}
	
	@Override
	public String refresh() {
		String url = super.showPage();
		selectionContext.setUrl(url);
		modulePageManager.updateState();
		return url;
	}
	
	@Override
	public String first() {
		String url = super.first();
		modulePageManager.updateState();
		return url;
	}

	@Override
	public String back() {
		String url = super.back();
		modulePageManager.updateState();
		return url;
	}

	@Override
	public String next() {
		String url = super.next();
		modulePageManager.updateState();
		return url;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}

	@Override
	public String finish() {
		Module module = getInstance();
		moduleDataManager.saveModule(module);
		moduleEventManager.fireSavedEvent(module);
		String url = selectionContext.popOrigin();
		return url;
	}
	
	@Override
	public String cancel() {
		Module module = getInstance();
		//TODO take this out soon
		if (module == null)
			module = new Module();
		moduleEventManager.fireCancelledEvent(module);
		String url = selectionContext.popOrigin();
		return url;
	}

	public String populateDefaultValues() {
		Module module = selectionContext.getSelection("module");
		String name = module.getName();
		if (StringUtils.isEmpty(name)) {
			display = getFromSession("display");
			display.setModule("moduleWizard");
			display.error("Module name must be specified");
			return null;
		}
		
		Project project = selectionContext.getSelection("project");
		
		String nameCapped = NameUtil.capName(name);
		String nameUncapped = NameUtil.uncapName(name);
		return getUrl();
	}
	
}
