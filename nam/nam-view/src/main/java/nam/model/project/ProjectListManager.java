package nam.model.project;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractDomainListManager;
import org.aries.ui.event.Cancelled;
import org.aries.ui.event.Export;
import org.aries.ui.event.Refresh;
import org.aries.ui.manager.ExportManager;

import nam.model.Project;
import nam.model.util.ProjectUtil;
import nam.ui.design.SelectionContext;


@SessionScoped
@Named("projectListManager")
public class ProjectListManager extends AbstractDomainListManager<Project, ProjectListObject> implements Serializable {
	
	@Inject
	private ProjectDataManager projectDataManager;
	
	@Inject
	private ProjectEventManager projectEventManager;
	
	@Inject
	private ProjectInfoManager projectInfoManager;
	
	@Inject
	private SelectionContext selectionContext;
	
	
	@Override
	public String getClientId() {
		return "projectList";
	}
	
	@Override
	public String getTitle() {
		return "Project List";
	}
	
	@Override
	public Object getRecordKey(Project project) {
		return ProjectUtil.getKey(project);
	}
	
	@Override
	public String getRecordName(Project project) {
		return ProjectUtil.getLabel(project);
	}
	
	@Override
	protected Class<Project> getRecordClass() {
		return Project.class;
	}
	
	@Override
	protected Project getRecord(ProjectListObject rowObject) {
		return rowObject.getProject();
	}
	
	@Override
	public Project getSelectedRecord() {
		return super.getSelectedRecord();
	}
	
	public String getSelectedRecordLabel() {
		return selectedRecord != null ? ProjectUtil.getLabel(selectedRecord) : null;
	}
	
	@Override
	public void setSelectedRecord(Project project) {
		super.setSelectedRecord(project);
		fireSelectedEvent(project);
	}
	
	protected void fireSelectedEvent(Project project) {
		projectEventManager.fireSelectedEvent(project);
	}
	
	public boolean isSelected(Project project) {
		Project selection = selectionContext.getSelection("project");
		boolean selected = selection != null && selection.equals(project);
		//return projectDataManager.isSelected(project);
		return selected;
	}
	
	@Override
	protected ProjectListObject createRowObject(Project project) {
		ProjectListObject listObject = new ProjectListObject(project);
		listObject.setSelected(isSelected(project));
		return listObject;
	}
	
	@Override
	public void reset() {
		refresh();
	}
	
	@Override
	public void initialize() {
		if (recordList != null)
			initialize(recordList);
		else refreshModel();
	}
	
	public void handleRefresh(@Observes @Refresh Object object) {
		//refreshModel();
	}
	
	@Override
	public void refreshModel() {
		refreshModel(createRecordList());
	}
	
	@Override
	protected Collection<Project> createRecordList() {
		try {
			Collection<Project> projectList = projectDataManager.getProjectList();
			if (projectList != null)
				return projectList;
			return recordList;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	public String viewProject() {
		return viewProject(selectedRecordKey);
	}
	
	public String viewProject(Object recordKey) {
		Project project = recordByKeyMap.get(recordKey);
		return viewProject(project);
	}
	
	public String viewProject(Project project) {
		String url = projectInfoManager.viewProject(project);
		return url;
	}
	
	public String editProject() {
		return editProject(selectedRecordKey);
	}
	
	public String editProject(Object recordKey) {
		Project project = recordByKeyMap.get(recordKey);
		return editProject(project);
	}
	
	public String editProject(Project project) {
		String url = projectInfoManager.editProject(project);
		return url;
	}
	
	public void removeProject() {
		removeProject(selectedRecordKey);
	}
	
	public void removeProject(Object recordKey) {
		Project project = recordByKeyMap.get(recordKey);
		removeProject(project);
	}
	
	public void removeProject(Project project) {
		try {
			if (projectDataManager.removeProject(project))
			clearSelection();
			refresh();
			
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public void cancelProject(@Observes @Cancelled Project project) {
		try {
			//Object key = ProjectUtil.getKey(project);
			//recordByKeyMap.put(key, project);
			initialize(recordByKeyMap.values());
			BeanContext.removeFromSession("project");
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	public boolean validateProject(Collection<Project> projectList) {
		return ProjectUtil.validate(projectList);
	}
	
	public void exportProjectList(@Observes @Export String tableId) {
		//String tableId = "pageForm:projectListTable";
		ExportManager exportManager = BeanContext.getFromSession("org.aries.exportManager");
		exportManager.exportToXLS(tableId);
	}
	
}
