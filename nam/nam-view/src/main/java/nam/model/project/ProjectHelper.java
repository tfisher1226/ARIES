package nam.model.project;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.manager.AbstractElementHelper;

import nam.model.Application;
import nam.model.Module;
import nam.model.Project;
import nam.model.application.ApplicationListManager;
import nam.model.application.ApplicationListObject;
import nam.model.module.ModuleListManager;
import nam.model.module.ModuleListObject;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;


@SessionScoped
@Named("projectHelper")
public class ProjectHelper extends AbstractElementHelper<Project> implements Serializable {
	
	@Override
	public boolean isEmpty(Project project) {
		return ProjectUtil.isEmpty(project);
	}
	
	@Override
	public String toString(Project project) {
		return ProjectUtil.toString(project);
	}
	
	@Override
	public String toString(Collection<Project> projectList) {
		return ProjectUtil.toString(projectList);
	}
	
	@Override
	public boolean validate(Project project) {
		return ProjectUtil.validate(project);
	}
	
	@Override
	public boolean validate(Collection<Project> projectList) {
		return ProjectUtil.validate(projectList);
	}
	
	public DataModel<ApplicationListObject> getApplications(Project project) {
		if (project == null)
			return null;
		Collection<Application> applications = ProjectUtil.getApplications(project);
		return getApplications(applications);
	}
	
	public DataModel<ApplicationListObject> getApplications(Collection<Application> applicationsList) {
		ApplicationListManager applicationListManager = BeanContext.getFromSession("applicationListManager");
		DataModel<ApplicationListObject> dataModel = applicationListManager.getDataModel(applicationsList);
		return dataModel;
	}
	
	public DataModel<ModuleListObject> getModules(Project project) {
		if (project == null)
			return null;
		Set<Module> modules = ModuleUtil.getModules(project.getModules());
		return getModules(modules);
	}
	
	public DataModel<ModuleListObject> getModules(Collection<Module> modulesList) {
		ModuleListManager moduleListManager = BeanContext.getFromSession("moduleListManager");
		DataModel<ModuleListObject> dataModel = moduleListManager.getDataModel(modulesList);
		return dataModel;
	}
	
}
