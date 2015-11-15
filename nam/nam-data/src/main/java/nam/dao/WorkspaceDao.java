package nam.dao;

import java.util.Collection;
import java.util.List;

import nam.entity.WorkspaceEntity;

import org.aries.common.dao.Dao;


public interface WorkspaceDao<T extends WorkspaceEntity> extends Dao<T> {
	
	public T getWorkspaceRecordById(Long id);
	
	public T getWorkspaceRecordByName(String name);
	
	public List<T> getAllWorkspaceRecords();
	
	public List<T> getWorkspaceRecordsByUser(String user);
	
	public List<T> getWorkspaceRecordsByPage(int pageIndex, int pageSize);
	
	public Long addWorkspaceRecord(T workspaceRecord);
	
	public List<Long> addWorkspaceRecords(Collection<T> workspaceRecords);
	
	public void saveWorkspaceRecord(T workspaceRecord);
	
	public void saveWorkspaceRecords(Collection<T> workspaceRecords);
	
	public void removeAllWorkspaceRecords();
	
	public void removeWorkspaceRecord(T workspaceRecord);
	
	public void removeWorkspaceRecords(Collection<T> workspaceRecords);
	
}
