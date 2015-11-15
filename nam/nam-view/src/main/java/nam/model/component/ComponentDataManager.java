package nam.model.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Component;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ApplicationUtil;
import nam.model.util.ComponentUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("componentDataManager")
public class ComponentDataManager implements Serializable {
	
	@Inject
	private ComponentEventManager componentEventManager;
	
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
	
	public Collection<Component> getComponentList() {
		if (scope == null)
			return null;
		
		if (scope.equals("projectSelection")) {
			return getComponentList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getComponentList_ForApplicationSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("service")) {
			return getComponentList((Service) owner);
		} else {
			return getDefaultList();
		}
	}

	public Collection<Component> getComponentList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectSelection);
		Collection<Component> components = ApplicationUtil.getComponents(applicationList);
		ComponentUtil.sortRecords(components);
		return components;
	}

	public Collection<Component> getComponentList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Component> components = ApplicationUtil.getComponents(applicationSelection);
		ComponentUtil.sortRecords(components);
		return components;
	}
	
	public Collection<Component> getComponentList(Service service) {
		Collection<Component> componentList = ServiceUtil.getComponents(service);
		if (service.getProcess() != null)
			componentList.add(service.getProcess());
		return componentList;
	}

	public Collection<Component> getDefaultList() {
		return null;
	}

	public void saveComponent(Component component) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("service")) {
				ServiceUtil.addComponent((Service) owner, component);
			}
		}
	}
	
	public boolean removeComponent(Component component) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("service")) {
				return ServiceUtil.removeComponent((Service) owner, component);
			}
		}
		return false;
	}
	
}
