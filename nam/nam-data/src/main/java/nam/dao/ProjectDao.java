package nam.dao;

import java.util.Collection;
import java.util.List;

import nam.entity.ProjectEntity;

import org.aries.common.dao.Dao;


public interface ProjectDao<T extends ProjectEntity> extends Dao<T> {
	
	public T getProjectRecordById(Long id);
	
	public T getProjectRecordByName(String name);
	
	public List<T> getAllProjectRecords();
	
	public List<T> getProjectRecordsByOwner(String owner);
	
	public List<T> getProjectRecordsByPage(int pageIndex, int pageSize);
	
	public Long addProjectRecord(T projectRecord);
	
	public List<Long> addProjectRecords(Collection<T> projectRecords);
	
	public void saveProjectRecord(T projectRecord);
	
	public void saveProjectRecords(Collection<T> projectRecords);
	
	public void removeAllProjectRecords();
	
	public void removeProjectRecord(T projectRecord);
	
	public void removeProjectRecords(Collection<T> projectRecords);
	
}
