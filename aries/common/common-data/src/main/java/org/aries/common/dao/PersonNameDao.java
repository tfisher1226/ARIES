package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.PersonNameEntity;


public interface PersonNameDao<T extends PersonNameEntity> extends Dao<T> {
	
	public T getPersonNameRecordById(Long id);
	
	public List<T> getAllPersonNameRecords();
	
	public List<T> getPersonNameRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPersonNameRecord(T personNameRecord);
	
	public List<Long> addPersonNameRecords(Collection<T> personNameRecords);
	
	public void savePersonNameRecord(T personNameRecord);
	
	public void savePersonNameRecords(Collection<T> personNameRecords);
	
	public void removeAllPersonNameRecords();
	
	public void removePersonNameRecord(T personNameRecord);
	
	public void removePersonNameRecords(Collection<T> personNameRecords);
	
}
