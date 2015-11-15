package nam.model.project;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractSelectManager;

import nam.model.Project;
import nam.model.util.ProjectUtil;


@SessionScoped
@Named("projectSelectManager")
public class ProjectSelectManager extends AbstractSelectManager<Project, ProjectListObject> implements Serializable {
	
	@Inject
	private ProjectDataManager projectDataManager;
	
	@Inject
	private ProjectHelper projectHelper;
	
	
	@Override
	public String getClientId() {
		return "projectSelect";
	}
	
	@Override
	public String getTitle() {
		return "Project Selection";
	}
	
	@Override
	protected Class<Project> getRecordClass() {
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
	
	protected ProjectHelper getProjectHelper() {
		return BeanContext.getFromSession("projectHelper");
	}
	
	protected ProjectListManager getProjectListManager() {
		return BeanContext.getFromSession("projectListManager");
	}
	
	@Override
	public void initialize() {
		initializeContext(); 
		refreshProjectList();
		populate(selectedRecords);
	}
	
	@Override
	public void populateItems(Collection<Project> recordList) {
		ProjectListManager projectListManager = getProjectListManager();
		DataModel<ProjectListObject> dataModel = projectListManager.getDataModel(recordList);
		setSelectedItems(dataModel);
	}
	
	public void refreshProjectList() {
		masterList = refreshRecords();
	}
	
	@Override
	protected Collection<Project> refreshRecords() {
		try {
			Collection<Project> records = projectDataManager.getProjectList();
			return records;
		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
	
	@Override
	public void activate() {
		initialize();
		super.show();
	}
	
	@Override
	public String submit() {
		setModule(targetField);
		return super.submit();
	}
	
	@Override
	public void sortRecords() {
		sortRecords(recordList);
	}
	
	@Override
	public void sortRecords(List<Project> projectList) {
		Collections.sort(projectList, new Comparator<Project>() {
			public int compare(Project project1, Project project2) {
				String text1 = ProjectUtil.toString(project1);
				String text2 = ProjectUtil.toString(project2);
				return text1.compareTo(text2);
			}
		});
	}
	
}
