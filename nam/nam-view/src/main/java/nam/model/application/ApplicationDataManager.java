package nam.model.application;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Domain;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.DomainUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("applicationDataManager")
public class ApplicationDataManager implements Serializable {
	
	@Inject
	private ApplicationEventManager applicationEventManager;
	
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
	
	public Collection<Application> getApplicationList() {
		if (scope == null)
			return null;

		if (scope.equals("projectSelection")) {
			return getApplicationList_ForProjectSelection();
		}
		
		if (scope.equals("domainSelection")) {
			return getApplicationList_ForDomainSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("project")) {
			return getApplicationList_ForProject((Project) owner);

		} else if (scope.equals("domain")) {
			return getApplicationList_ForDomain((Domain) owner);

		} else {
			return getDefaultList();
		}
	}
	
	protected Collection<Application> getApplicationList_ForProject(Project project) {
		return ProjectUtil.getApplications(project);
	}
	
	protected Collection<Application> getApplicationList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectSelection);
		return applicationList;
	}

	public Collection<Application> getApplicationList_ForDomain(Domain domain) {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationList = DomainUtil.getApplications(projectList, domain);
		return applicationList;
	}
	
	protected Collection<Application> getApplicationList_ForDomainSelection() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Domain> domainSelection = selectionContext.getSelection("domainSelection");
		Collection<Application> applicationList = DomainUtil.getApplications(projectList, domainSelection);
		return applicationList;
	}
	
	public Collection<Application> getApplicationList(String selector) {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
		return applicationList;
	}

	public Collection<Application> getDefaultList() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
		return applicationList;
	}
	
	public void saveApplication(Application application) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				ProjectUtil.addApplication((Project) owner, application);
		
			} else if (scope.equals("domain")) {
				DomainUtil.addApplication((Domain) owner, application);
			}
		}
	}
	
	public boolean removeApplication(Application application) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("project")) {
				return ProjectUtil.removeApplication((Project) owner, application);
		
			} else if (scope.equals("domain")) {
				return DomainUtil.removeApplication((Domain) owner, application);
			}
		}
		return false;
	}

}
