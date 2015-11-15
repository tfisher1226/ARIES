package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.EmailAddressEntity;


public interface EmailAddressDao<T extends EmailAddressEntity> extends Dao<T> {
	
	public T getEmailAddressRecordById(Long id);
	
	public List<T> getAllEmailAddressRecords();
	
	public List<T> getEmailAddressRecordsByPage(int pageIndex, int pageSize);
	
	public Long addEmailAddressRecord(T emailAddressRecord);
	
	public List<Long> addEmailAddressRecords(Collection<T> emailAddressRecords);
	
	public void saveEmailAddressRecord(T emailAddressRecord);
	
	public void saveEmailAddressRecords(Collection<T> emailAddressRecords);
	
	public void removeAllEmailAddressRecords();
	
	public void removeEmailAddressRecord(T emailAddressRecord);
	
	public void removeEmailAddressRecords(Collection<T> emailAddressRecords);
	
}
