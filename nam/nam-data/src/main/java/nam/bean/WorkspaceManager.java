package nam.bean;

import java.util.List;

import nam.model.Workspace;


public interface WorkspaceManager {
	
	public void initialize();
	
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
	
}
