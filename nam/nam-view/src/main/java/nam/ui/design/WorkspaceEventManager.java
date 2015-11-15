package nam.ui.design;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;


@SessionScoped
@Named("workspaceEventManager")
public class WorkspaceEventManager extends AbstractEventManager<Object> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	protected Object getInstance() {
		return null;
	}

	public Project getProject() {
		return selectionContext.getSelection("project");
	}

	public List<Project> getProjectList() {
		return selectionContext.getSelection("projectList");
	}

	public void fireRefreshEvent() {
		super.fireRefreshEvent(new Object());
	}

}
