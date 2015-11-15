package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.PersonEntity;


public interface PersonDao<T extends PersonEntity> extends Dao<T> {
	
	public T getPersonRecordById(Long id);
	
	public List<T> getAllPersonRecords();
	
	public List<T> getPersonRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPersonRecord(T personRecord);
	
	public List<Long> addPersonRecords(Collection<T> personRecords);
	
	public void savePersonRecord(T personRecord);
	
	public void savePersonRecords(Collection<T> personRecords);
	
	public void removeAllPersonRecords();
	
	public void removePersonRecord(T personRecord);
	
	public void removePersonRecords(Collection<T> personRecords);
	
}
