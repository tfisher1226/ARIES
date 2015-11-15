package nam.model.modules;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Module;
import nam.model.Modules;
import nam.model.module.ModuleListManager;
import nam.model.module.ModuleListObject;
import nam.model.util.ModuleUtil;
import nam.model.util.ModulesUtil;


@SessionScoped
@Named("modulesHelper")
public class ModulesHelper extends AbstractElementHelper<Modules> implements Serializable {
	
	@Override
	public boolean isEmpty(Modules modules) {
		return ModulesUtil.isEmpty(modules);
	}
	
	@Override
	public String toString(Modules modules) {
		return ModulesUtil.toString(modules);
	}
	
	@Override
	public String toString(Collection<Modules> modulesList) {
		return ModulesUtil.toString(modulesList);
	}
	
	@Override
	public boolean validate(Modules modules) {
		return ModulesUtil.validate(modules);
	}
	
	@Override
	public boolean validate(Collection<Modules> modulesList) {
		return ModulesUtil.validate(modulesList);
	}
	
	public DataModel<ModuleListObject> getModules(Modules modules) {
		if (modules == null)
			return null;
		return getModules(ModuleUtil.getModules(modules));
	}
	
	public DataModel<ModuleListObject> getModules(Collection<Module> modulesList) {
		ModuleListManager moduleListManager = BeanContext.getFromSession("moduleListManager");
		DataModel<ModuleListObject> dataModel = moduleListManager.getDataModel(modulesList);
		return dataModel;
	}
	
}
