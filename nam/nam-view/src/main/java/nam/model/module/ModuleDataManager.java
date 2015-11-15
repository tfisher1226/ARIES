package nam.model.module;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Module;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("moduleDataManager")
public class ModuleDataManager implements Serializable {
	
	@Inject
	private ModuleEventManager moduleEventManager;
	
	@Inject
	private SelectionContext selectionContext;

	private String scope;
	
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	protected <T> T getOwner() {
		T owner = selectionContext.getSelection(scope);
		return owner;
	}
	
	public Collection<Module> getModuleList() {
		if (scope == null)
			return null;
		
		if (scope.equals("projectSelection")) {
			return getModuleList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getModuleList_ForApplicationSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("project")) {
			return getModuleList_ForProject((Project) owner);
		
		} else if (scope.equals("application")) {
			return getModuleList_ForApplication((Application) owner);
		
		} else {
			return getDefaultList();
		}
	}

	protected Collection<Module> getModuleList_ForProject(Project project) {
		return ProjectUtil.getModules(project);
	}

	protected Collection<Module> getModuleList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Module> moduleList = ProjectUtil.getModules(projectSelection);
		return moduleList;
	}
	
	protected Collection<Module> getModuleList_ForApplication(Application application) {
		return ApplicationUtil.getModules(application);
	}

	protected Collection<Module> getModuleList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Module> moduleList = ApplicationUtil.getModules(applicationSelection);
		return moduleList;
	}
	
	public Collection<Module> getDefaultList() {
		return null;
	}

	public void saveModule(Module module) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				ProjectUtil.addModule((Project) owner, module);
		
			} else if (scope.equals("application")) {
				ApplicationUtil.addModule((Application) owner, module);
			}
		}
	}
	
	public boolean removeModule(Module module) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				return ProjectUtil.removeModule((Project) owner, module);
		
			} else if (scope.equals("application")) {
				return ApplicationUtil.removeModule((Application) owner, module);
			}
		}
		return false;
	}
	
}
