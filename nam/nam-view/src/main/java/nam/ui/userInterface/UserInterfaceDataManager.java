package nam.ui.userInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Application;
import nam.model.Project;
import nam.model.util.ApplicationUtil;
import nam.model.util.ProjectUtil;
import nam.ui.UserInterface;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("userInterfaceDataManager")
public class UserInterfaceDataManager implements Serializable {
	
	@Inject
	private UserInterfaceEventManager userInterfaceEventManager;
	
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
	public Collection<UserInterface> getUserInterfaceList() {
		if (scope == null)
			return null;
		
		Object owner = getOwner();
		if (owner == null)
			return null;
		
		if (scope.equals("application")) {
			return getUserInterfaceList((Application) owner);
		} else {
			return getDefaultList();
		}
	}

	protected Collection<UserInterface> getUserInterfaceList(Application application) {
		return ApplicationUtil.getUserInterfaces(application);
	}

	protected Collection<UserInterface> getDefaultList() {
		Collection<UserInterface> list = new ArrayList<UserInterface>();
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		Collection<Application> applicationList = ProjectUtil.getApplications(projectList);
		Iterator<Application> iterator = applicationList.iterator();
		while (iterator.hasNext()) {
			Application application = iterator.next();
			Collection<UserInterface> userInterfaceList = getUserInterfaceList(application);
			list.addAll(userInterfaceList);
		}
		return list;
	}

	public void saveUserInterface(UserInterface userInterface) {
		if (scope != null) {
			if (scope.equals("application")) {
				ApplicationUtil.addUserInterface((Application) getOwner(), userInterface);
			}
		}
	} 

	public boolean removeUserInterface(UserInterface userInterface) {
		if (scope != null) {
			if (scope.equals("application")) {
				return ApplicationUtil.removeUserInterface((Application) getOwner(), userInterface);
			}
		}
		return false;
	}
	
}
