package nam.model.project;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.Display;
import org.aries.ui.event.Add;
import org.aries.ui.event.Remove;
import org.aries.util.Validator;

import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.AbstractNamRecordManager;
import nam.ui.design.SelectionContext;
import nam.ui.design.WorkspaceEventManager;


@SessionScoped
@Named("projectInfoManager")
public class ProjectInfoManager extends AbstractNamRecordManager<Project> implements Serializable {
	
	@Inject
	private ProjectWizard projectWizard;
	
	@Inject
	private ProjectDataManager projectDataManager;
	
	@Inject
	private ProjectPageManager projectPageManager;
	
	@Inject
	private ProjectEventManager projectEventManager;
	
	@Inject
	private WorkspaceEventManager workspaceEventManager;
	
	@Inject
	private ProjectHelper projectHelper;
	
	@Inject
	private SelectionContext selectionContext;

	
	public ProjectInfoManager() {
		setInstanceName("project");
	}
	
	
	public Project getProject() {
		return getRecord();
	}
	
	public Project getSelectedProject() {
		return selectionContext.getSelection("project");
	}
	
	@Override
	public Class<Project> getRecordClass() {
		return Project.class;
	}
	
	@Override
	public boolean isEmpty(Project project) {
		return projectHelper.isEmpty(project);
	}
	
	@Override
	public String toString(Project project) {
		return projectHelper.toString(project);
	}
	
	@Override
	public void initialize() {
		Project project = selectionContext.getSelection("project");
		if (project != null)
			initialize(project);
	}
	
	protected void initialize(Project project) {
		projectWizard.initialize(project);
		setContext("project", project);
	}
	
	@Override
	public String newRecord() {
		return newProject();
	}
	
	public String newProject() {
		try {
			Project project = create();
			selectionContext.resetOrigin();
			selectionContext.setSelection("project",  project);
			String url = projectPageManager.initializeProjectCreationPage(project);
			projectPageManager.pushContext(projectWizard);
			initialize(project);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public Project create() {
		Project project = ProjectUtil.create();
		return project;
	}
	
	@Override
	public Project clone(Project project) {
		project = ProjectUtil.clone(project);
		return project;
	}
	
	@Override
	public String viewRecord() {
		return viewProject();
	}
	
	public String viewProject() {
		Project project = selectionContext.getSelection("project");
		String url = viewProject(project);
		return url;
	}
	
	public String viewProject(Project project) {
		try {
			String url = projectPageManager.initializeProjectSummaryView(project);
			projectPageManager.pushContext(projectWizard);
			initialize(project);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public String editRecord() {
		return editProject();
	}
	
	public String editProject() {
		Project project = selectionContext.getSelection("project");
		String url = editProject(project);
		return url;
	}
	
	public String editProject(Project project) {
		try {
			//project = clone(project);
			selectionContext.resetOrigin();
			selectionContext.setSelection("project",  project);
			String url = projectPageManager.initializeProjectUpdatePage(project);
			projectPageManager.pushContext(projectWizard);
			initialize(project);
			return url;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public void saveProject() {
		Project project = getProject();
		if (validateProject(project)) {
			if (isImmediate())
				persistProject(project);
			outject("project", project);
		}
	}
	
	public void persistProject(Project project) {
		saveProject(project);
	}
	
	public void saveProject(Project project) {
		try {
			saveProjectToSystem(project);
			projectEventManager.fireAddedEvent(project);
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	protected void saveProjectToSystem(Project project) {
		projectDataManager.saveProject(project);
	}
	
	public void handleSaveProject(@Observes @Add Project project) {
		saveProject(project);
	}
	
	public void addProject(Project project) {
		try {
			//TODO
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void enrichProject(Project project) {
		//nothing for now
	}
	
	@Override
	public boolean validate(Project project) {
		return validateProject(project);
	}
	
	public boolean validateProject(Project project) {
		Validator validator = getValidator();
		boolean isValid = ProjectUtil.validate(project);
		Display display = getFromSession("display");
		display.setModule("projectInfo");
		display.addErrors(validator.getMessages());
		//FacesContext.getCurrentInstance().isValidationFailed()
		setValidated(isValid);
		return isValid;
	}
	
	public void promptRemoveProject() {
		display = getFromSession("display");
		display.setModule("projectInfo");
		Project project = selectionContext.getSelection("project");
		if (project == null) {
			display.error("Project record must be selected.");
		}
	}
	
	public String handleRemoveProject(@Observes @Remove Project project) {
		display = getFromSession("display");
		display.setModule("projectInfo");
		try {
			display.info("Removing Project "+ProjectUtil.getLabel(project)+" from the system.");
			removeProjectFromSystem(project);
			selectionContext.clearSelection("project");
			projectEventManager.fireClearSelectionEvent();
			projectEventManager.fireRemovedEvent(project);
			workspaceEventManager.fireRefreshEvent();
			return null;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	protected void removeProjectFromSystem(Project project) {
		if (projectDataManager.removeProject(project))
		setRecord(null);
	}
	
	public void cancelProject() {
		BeanContext.removeFromSession("project");
		projectPageManager.removeContext(projectWizard);
	}
	
}
