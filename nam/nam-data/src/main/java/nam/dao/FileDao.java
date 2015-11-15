package nam.dao;

import java.util.Collection;
import java.util.List;

import nam.entity.FileEntity;

import org.aries.common.dao.Dao;


public interface FileDao<T extends FileEntity> extends Dao<T> {
	
	public T getFileRecordById(Long id);
	
	public T getFileRecordByName(String name);
	
	public List<T> getAllFileRecords();
	
	public List<T> getFileRecordsByOwner(String owner);
	
	public List<T> getFileRecordsByPage(int pageIndex, int pageSize);
	
	public Long addFileRecord(T fileRecord);
	
	public List<Long> addFileRecords(Collection<T> fileRecords);
	
	public void saveFileRecord(T fileRecord);
	
	public void saveFileRecords(Collection<T> fileRecords);
	
	public void removeAllFileRecords();
	
	public void removeFileRecord(T fileRecord);
	
	public void removeFileRecords(Collection<T> fileRecords);
	
}
