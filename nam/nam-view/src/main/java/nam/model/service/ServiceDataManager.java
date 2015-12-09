package nam.model.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Domain;
import nam.model.Module;
import nam.model.Project;
import nam.model.Service;
import nam.model.util.ApplicationUtil;
import nam.model.util.DomainUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("serviceDataManager")
public class ServiceDataManager implements Serializable {
	
	@Inject
	private ServiceEventManager serviceEventManager;
	
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
	
	@SuppressWarnings("unchecked")
	public Collection<Service> getServiceList() {
		if (scope == null)
			return null;
		
		if (scope.equals("projectSelection")) {
			return getServiceList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getServiceList_ForApplicationSelection();
		}
		
		if (scope.equals("moduleSelection")) {
			return getServiceList_ForModuleSelection();
		}
		
		if (scope.equals("domainSelection")) {
			return getServiceList_ForDomainSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("project")) {
			return getServiceList_ForProject((Project) owner);
			
		} else if (scope.equals("projectList")) {
			return getServiceList_ForProjectList((Collection<Project>) owner);

		} else if (scope.equals("application")) {
			return getServiceList_ForApplication((Application) owner);
			
		} else if (scope.equals("applicationList")) {
			return getServiceList_ForApplicationList((Collection<Application>) owner);
			
		} else if (scope.equals("module")) {
			return getServiceList_ForModule((Module) owner);
			
		} else if (scope.equals("moduleList")) {
			return getServiceList_ForModuleList((Collection<Module>) owner);
			
		} else if (scope.equals("domain")) {
			return getServiceList_ForDomain((Domain) owner);
			
		} else {
			return getDefaultList();
		}
	}

	protected Collection<Service> getServiceList_ForProject(Project project) {
		Collection<Service> serviceList = ProjectUtil.getServices(project);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForProjectList(Collection<Project> projectList) {
		Collection<Service> serviceList = ProjectUtil.getServices(projectList);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Service> serviceList = ProjectUtil.getServices(projectSelection);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForApplication(Application application) {
		Collection<Service> serviceList = ApplicationUtil.getServices(application);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForApplicationList(Collection<Application> applicationList) {
		Collection<Service> serviceList = ApplicationUtil.getServices(applicationList);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}

	protected Collection<Service> getServiceList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Service> serviceList = ApplicationUtil.getServices(applicationSelection);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForModule(Module module) {
		Collection<Service> serviceList = ModuleUtil.getServices(module);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForModuleList(Collection<Module> moduleList) {
		Collection<Service> serviceList = ModuleUtil.getServices(moduleList);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}

	protected Collection<Service> getServiceList_ForModuleSelection() {
		Collection<Module> moduleSelection = selectionContext.getSelection("moduleSelection");
		Collection<Service> serviceList = ModuleUtil.getServices(moduleSelection);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForDomain(Domain domain) {
		Collection<Service> serviceList = DomainUtil.getServices(domain);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	protected Collection<Service> getServiceList_ForDomainSelection() {
		Collection<Domain> domainSelection = selectionContext.getSelection("domainSelection");
		Collection<Service> serviceList = DomainUtil.getServices(domainSelection);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}
	
	public Collection<Service> getDefaultList() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Service> serviceList = ProjectUtil.getServices(projectList);
		serviceList = ServiceUtil.sortRecords(serviceList);
		return serviceList;
	}

	public void saveService(Service service) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				//ProjectUtil.addService((Project) owner, service);
		
			} else if (scope.equals("application")) {
				ApplicationUtil.addService((Application) owner, service);
		
			} else if (scope.equals("module")) {
				ModuleUtil.addService((Module) owner, service);
		
			} else if (scope.equals("domain")) {
				DomainUtil.addService((Domain) owner, service);
			}
		}
	}
	
	public boolean removeService(Service service) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				//return ProjectUtil.removeService((Project) owner, service);
		
			} else if (scope.equals("application")) {
				return ApplicationUtil.removeService((Application) owner, service);
		
			} else if (scope.equals("module")) {
				return ModuleUtil.removeService((Module) owner, service);
		
			} else if (scope.equals("domain")) {
				return DomainUtil.removeService((Domain) owner, service);
			}
		}
		return false;
	}

}
