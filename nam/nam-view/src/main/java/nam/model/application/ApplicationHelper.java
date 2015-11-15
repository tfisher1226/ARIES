package nam.model.application;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Module;
import nam.model.module.ModuleListManager;
import nam.model.module.ModuleListObject;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleUtil;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;


@SessionScoped
@Named("applicationHelper")
public class ApplicationHelper extends AbstractElementHelper<Application> implements Serializable {
	
	@Override
	public boolean isEmpty(Application application) {
		return ApplicationUtil.isEmpty(application);
	}
	
	@Override
	public String toString(Application application) {
		return ApplicationUtil.toString(application);
	}
	
	@Override
	public String toString(Collection<Application> applicationList) {
		return ApplicationUtil.toString(applicationList);
	}
	
	@Override
	public boolean validate(Application application) {
		return ApplicationUtil.validate(application);
	}
	
	@Override
	public boolean validate(Collection<Application> applicationList) {
		return ApplicationUtil.validate(applicationList);
	}
	
//	public DataModel<ModuleListObject> getModules(Application application) {
//		if (application == null)
//			return null;
//		Set<Module> modules = ModuleUtil.getModules(application.getModules());
//		return getModules(modules);
//	}
//	
//	public DataModel<ModuleListObject> getModules(Collection<Module> modulesList) {
//		ModuleListManager moduleListManager = BeanContext.getFromSession("moduleListManager");
//		DataModel<ModuleListObject> dataModel = moduleListManager.getDataModel(modulesList);
//		return dataModel;
//	}
	
}
