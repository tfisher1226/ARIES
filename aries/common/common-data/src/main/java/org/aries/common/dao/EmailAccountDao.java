package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.EmailAccountEntity;


public interface EmailAccountDao<T extends EmailAccountEntity> extends Dao<T> {
	
	public T getEmailAccountRecordById(Long id);
	
	public T getEmailAccountRecordByUserId(String userId);
	
	public List<T> getAllEmailAccountRecords();
	
	public List<T> getEmailAccountRecordsByPage(int pageIndex, int pageSize);
	
	public Long addEmailAccountRecord(T emailAccountRecord);
	
	public List<Long> addEmailAccountRecords(Collection<T> emailAccountRecords);
	
	public void saveEmailAccountRecord(T emailAccountRecord);
	
	public void saveEmailAccountRecords(Collection<T> emailAccountRecords);
	
	public void removeAllEmailAccountRecords();
	
	public void removeEmailAccountRecord(T emailAccountRecord);
	
	public void removeEmailAccountRecords(Collection<T> emailAccountRecords);
	
}
