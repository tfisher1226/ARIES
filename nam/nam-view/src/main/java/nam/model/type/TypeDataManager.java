package nam.model.type;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.Type;
import nam.model.util.ApplicationUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.TypeUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("typeDataManager")
public class TypeDataManager implements Serializable {
	
	@Inject
	private TypeEventManager typeEventManager;
	
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
	
	public Collection<Type> getTypeList() {
		if (scope == null)
			return null;

		if (scope.equals("projectSelection")) {
			return getTypeList_ForProjectSelection();
		}
		
		if (scope.equals("applicationSelection")) {
			return getTypeList_ForApplicationSelection();
		}
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (owner instanceof Namespace) {
			return getTypeList_FromNamespace((Namespace) owner);
			
		} else if (owner instanceof Application) {
			return getTypeList_FromApplication((Application) owner);
			
		} else {
			return getDefaultList();
		}
	}
	
	public Collection<Type> getTypeList_ForProjectSelection() {
		Collection<Project> projectSelection = selectionContext.getSelection("projectSelection");
		Collection<Namespace> namespaces = ProjectUtil.getNamespaces(projectSelection);
		List<Type> types = NamespaceUtil.getTypes(namespaces);
		TypeUtil.sortRecords(types);
		return types;
	}

	public Collection<Type> getTypeList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Namespace> namespaces = ApplicationUtil.getNamespaces(applicationSelection);
		List<Type> types = NamespaceUtil.getTypes(namespaces);
		TypeUtil.sortRecords(types);
		return types;
	}

	public Collection<Type> getTypeList_FromNamespace(Namespace namespace) {
		List<Type> types = NamespaceUtil.getTypes(namespace);
		TypeUtil.sortRecords(types);
		return types;
	}

	public Collection<Type> getTypeList_FromApplication(Application application) {
		Collection<Namespace> namespaces = ApplicationUtil.getNamespaces(application);
		List<Type> types = NamespaceUtil.getTypes(namespaces);
		TypeUtil.sortRecords(types);
		return types;
	}

	public Collection<Type> getDefaultList() {
		return null;
	}
	
	public void saveType(Type type) {
		if (scope != null) {
			Object owner = getOwner();
			if (owner instanceof Namespace) {
				NamespaceUtil.addType((Namespace) owner, type);
			}
		}
	}

	public boolean removeType(Type type) {
		if (scope != null) {
			Object owner = getOwner();
			if (owner instanceof Namespace) {
				return NamespaceUtil.removeType((Namespace) owner, type);
			}
		}
		return false;
	}
	
}
