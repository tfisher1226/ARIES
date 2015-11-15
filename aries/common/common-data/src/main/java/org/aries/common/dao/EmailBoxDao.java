package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.EmailBoxEntity;


public interface EmailBoxDao<T extends EmailBoxEntity> extends Dao<T> {
	
	public T getEmailBoxRecordById(Long id);
	
	public List<T> getAllEmailBoxRecords();
	
	public List<T> getEmailBoxRecordsByPage(int pageIndex, int pageSize);
	
	public Long addEmailBoxRecord(T emailBoxRecord);
	
	public List<Long> addEmailBoxRecords(Collection<T> emailBoxRecords);
	
	public void saveEmailBoxRecord(T emailBoxRecord);
	
	public void saveEmailBoxRecords(Collection<T> emailBoxRecords);
	
	public void removeAllEmailBoxRecords();
	
	public void removeEmailBoxRecord(T emailBoxRecord);
	
	public void removeEmailBoxRecords(Collection<T> emailBoxRecords);
	
}
