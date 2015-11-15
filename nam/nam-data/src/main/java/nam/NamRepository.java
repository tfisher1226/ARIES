package nam;

import java.util.List;

import nam.model.Project;
import nam.model.Workspace;


public interface NamRepository {
	
	public void clearContext();
	
	public List<Workspace> getAllWorkspaceRecords();
	
	public Workspace getWorkspaceRecordById(Long id);
	
	public Workspace getWorkspaceRecordByName(String name);
	
	public List<Workspace> getWorkspaceRecordsByUser(String user);
	
	public List<Workspace> getWorkspaceRecordsByPage(int pageIndex, int pageSize);
	
	public Long addWorkspaceRecord(Workspace workspace);
	
	public void saveWorkspaceRecord(Workspace workspace);
	
	public void removeAllWorkspaceRecords();
	
	public void removeWorkspaceRecord(Workspace workspace);
	
	public void removeWorkspaceRecord(Long id);
	
	public void importWorkspaceRecords();

	
	public List<Project> getAllProjectRecords();
	
	public Project getProjectRecordById(Long id);
	
	public Project getProjectRecordByName(String name);
	
	public List<Project> getProjectRecordsByUser(String user);
	
	public List<Project> getProjectRecordsByPage(int pageIndex, int pageSize);
	
	public Long addProjectRecord(Project project);
	
	public void saveProjectRecord(Project project);
	
	public void removeAllProjectRecords();
	
	public void removeProjectRecord(Project project);
	
	public void removeProjectRecord(Long id);
	
	public void importProjectRecords();
	
}
