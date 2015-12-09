package nam.model.project;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("projectDataManager")
public class ProjectDataManager implements Serializable {
	
	@Inject
	private ProjectEventManager projectEventManager;
	
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
	
//	public boolean isSelected(Project project) {
//		if (scope == null)
//			return false;
//		
//		if (scope.equals("project")) {
//			Project selection = selectionContext.getSelection("project");
//			boolean selected = selection != null && selection.equals(project);
//			return selected;
//			
//		} if (scope.equals("projectSet")) {
//			Collection<Project> projectSet = selectionContext.getSelection("project");
//			Iterator<Project> iterator = projectSet.iterator();
//			while (iterator.hasNext()) {
//				Project selectedProject = iterator.next();
//				if (selectedProject.equals(project)) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
	
	public Collection<Project> getProjectList() {
		if (scope == null)
			return null;
		
		//Object owner = getOwner();
		//if (owner == null)
		//	return null;
		
		return getDefaultList();
	}
	
	public Collection<Project> getDefaultList() {
		Collection<Project> projectList = selectionContext.getSelection("projectList");
		return projectList;
	}
	
	public void saveProject(Project project) {
		if (scope != null) {
			if (scope.equals("service")) {
				List<Project> projectList = selectionContext.getSelection("projectList");
				ProjectUtil.addProject(projectList, project);
			}
		}
	}
	
	public boolean removeProject(Project project) {
		if (scope != null) {
			if (scope.equals("service")) {
				List<Project> projectList = selectionContext.getSelection("projectList");
				return ProjectUtil.removeProject(projectList, project);
			}
		}
		return false;
	}
	
}
