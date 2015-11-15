package nam.bean;

import java.util.List;

import nam.model.Project;


public interface ProjectManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Project> getAllProjectRecords();
	
	public Project getProjectRecordById(Long id);
	
	public Project getProjectRecordByName(String name);
	
	public List<Project> getProjectRecordsByOwner(String owner);
	
	public List<Project> getProjectRecordsByPage(int pageIndex, int pageSize);
	
	public Long addProjectRecord(Project project);
	
	public void saveProjectRecord(Project project);
	
	public void removeAllProjectRecords();
	
	public void removeProjectRecord(Project project);
	
	public void removeProjectRecord(Long id);
	
	public void importProjectRecords();
	
}
