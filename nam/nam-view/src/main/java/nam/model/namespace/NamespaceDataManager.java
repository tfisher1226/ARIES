package nam.model.namespace;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Information;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ApplicationUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.ui.design.SelectionContext;

import org.aries.Assert;


@SessionScoped
@Named("namespaceDataManager")
public class NamespaceDataManager implements Serializable {
	
	@Inject
	private NamespaceEventManager namespaceEventManager;
	
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
	
	public Collection<Namespace> getNamespaceList() {
		if (scope == null)
			return null;
		
		if (scope.equals("projectSelection")) {
			return getNamespaceList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getNamespaceList_ForApplicationSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;

		if (owner instanceof Application) {
			return ApplicationUtil.getNamespaces((Application) owner);

		} else if (owner instanceof Module) {
			return ModuleUtil.getNamespaces((Module) owner);

		} else if (owner instanceof Information) {
			return InformationUtil.getNamespaces((Information) owner);
		} else {
			return getDefaultList();
		}
	}

	public Collection<Namespace> getNamespaceList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Namespace> namespaces = ProjectUtil.getNamespaces(projectSelection);
		NamespaceUtil.sortRecords(namespaces);
		return namespaces;
	}

	public Collection<Namespace> getNamespaceList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Namespace> namespaces = ApplicationUtil.getNamespaces(applicationSelection);
		NamespaceUtil.sortRecords(namespaces);
		return namespaces;
	}
	
	protected Collection<Namespace> getNamespaceList(Information information) {
		return InformationUtil.getNamespaces(information);
	}
	
	protected Collection<Namespace> getNamespaceList(Application application) {
		return ApplicationUtil.getNamespaces(application);
	}
	
	public Collection<Namespace> getDefaultList() {
		return null;
	}
	
	public void saveNamespace(Namespace namespace) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("information")) {
				InformationUtil.addNamespace((Information) owner, namespace);
		
//			} else if (scope.equals("application")) {
//				ApplicationUtil.addNamespace((Application) owner, namespace);
			}
		}
	}

	public boolean removeNamespace(Namespace namespace) {
		if (scope != null) {
			Object owner = getOwner();
		
			if (scope.equals("information")) {
			return InformationUtil.removeNamespace((Information) owner, namespace);
		
//			} else if (scope.equals("application")) {
//				return ApplicationUtil.removeNamespace((Application) owner, namespace);
			}
		}
		return false;
	}

}
