package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.EmailAddressListEntity;


public interface EmailAddressListDao<T extends EmailAddressListEntity> extends Dao<T> {
	
	public T getEmailAddressListRecordById(Long id);
	
	public List<T> getAllEmailAddressListRecords();
	
	public List<T> getEmailAddressListRecordsByPage(int pageIndex, int pageSize);
	
	public Long addEmailAddressListRecord(T emailAddressListRecord);
	
	public List<Long> addEmailAddressListRecords(Collection<T> emailAddressListRecords);
	
	public void saveEmailAddressListRecord(T emailAddressListRecord);
	
	public void saveEmailAddressListRecords(Collection<T> emailAddressListRecords);
	
	public void removeAllEmailAddressListRecords();
	
	public void removeEmailAddressListRecord(T emailAddressListRecord);
	
	public void removeEmailAddressListRecords(Collection<T> emailAddressListRecords);
	
}
