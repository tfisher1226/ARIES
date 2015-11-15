package nam.model.element;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.DomainUtil;
import nam.model.util.InformationUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("elementDataManager")
public class ElementDataManager implements Serializable {
	
	@Inject
	private ElementEventManager elementEventManager;
	
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
	
	public Collection<Element> getElementList() {
		if (scope == null)
			return null;
		
		if (scope.equals("applicationSelection")) {
			return getElementList_ForApplicationSelection();
		}
		
		if (scope.equals("moduleSelection")) {
			return getElementList_ForModuleSelection();
		}

		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("namespace")) {
			return getElementList((Namespace) getOwner());
		} else if (scope.equals("application")) {
			return getElementList((Application) getOwner());
		} else if (scope.equals("project")) {
			return getElementList((Project) getOwner());
		} else if (scope.equals("projectList")) {
			return getElementList((Collection<Project>) getOwner());
		} else {
			return getDefaultList();
		}
	}
	
	protected Collection<Element> getElementList_ForApplicationSelection() {
		Collection<Application> applicationSelection = selectionContext.getSelection("applicationSelection");
		Collection<Element> elementList = ApplicationUtil.getElements(applicationSelection);
		return elementList;
	}

	public Collection<Element> getElementList_ForModuleSelection() {
		Collection<Module> moduleSelection = selectionContext.getSelection("moduleSelection");
		Collection<Element> elementList = ModuleUtil.getElements(moduleSelection);
		return elementList;
	}
	
	public Collection<Element> getElementList(Namespace namespace) {
		Collection<Element> elementList = NamespaceUtil.getElements(namespace);
		return elementList;
	}

	public Collection<Element> getElementList(Application application) {
		Information informationBlock = ApplicationUtil.getInformation(application);
		Collection<Element> elementList = InformationUtil.getElements(informationBlock);
		return elementList;
	}

	public Collection<Element> getElementList(Project project) {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(project);
		Collection<Element> elementList = InformationUtil.getElements(informationBlocks);
		return elementList;
	}

	public Collection<Element> getElementList(Collection<Project> projectList) {
		Collection<Information> informationBlocks = ProjectUtil.getInformationBlocks(projectList);
		Collection<Element> elementList = InformationUtil.getElements(informationBlocks);
		return elementList;
	}

	public Collection<Element> getDefaultList() {
		return null;
	}

	public void saveElement(Element element) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("namespace")) {
				NamespaceUtil.addElement((Namespace) owner, element);
			}
		}
	}
	
	public boolean removeElement(Element element) {
		if (scope != null) {
			Object owner = getOwner();
			if (scope.equals("namespace")) {
				return NamespaceUtil.removeElement((Namespace) owner, element);
			}
		}
		return false;
	}
	
}
