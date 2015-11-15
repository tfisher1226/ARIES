package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.EmailMessageEntity;


public interface EmailMessageDao<T extends EmailMessageEntity> extends Dao<T> {
	
	public T getEmailMessageRecordById(Long id);
	
	public List<T> getAllEmailMessageRecords();
	
	public List<T> getEmailMessageRecordsByPage(int pageIndex, int pageSize);
	
	public Long addEmailMessageRecord(T emailMessageRecord);
	
	public List<Long> addEmailMessageRecords(Collection<T> emailMessageRecords);
	
	public void saveEmailMessageRecord(T emailMessageRecord);
	
	public void saveEmailMessageRecords(Collection<T> emailMessageRecords);
	
	public void removeAllEmailMessageRecords();
	
	public void removeEmailMessageRecord(T emailMessageRecord);
	
	public void removeEmailMessageRecords(Collection<T> emailMessageRecords);
	
}
