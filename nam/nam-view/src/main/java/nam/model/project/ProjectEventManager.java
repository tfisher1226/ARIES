package nam.model.project;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Project;
import nam.ui.design.AbstractEventManager;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("projectEventManager")
public class ProjectEventManager extends AbstractEventManager<Project> implements Serializable {

	@Inject
	private SelectionContext selectionContext;
	

	@Override
	public Project getInstance() {
		return selectionContext.getSelection("project");
	}

	public void removeProject() {
		Project project = getInstance();
		fireRemoveEvent(project);
	}
	
}
