package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.PhoneNumberEntity;


public interface PhoneNumberDao<T extends PhoneNumberEntity> extends Dao<T> {
	
	public T getPhoneNumberRecordById(Long id);
	
	public List<T> getAllPhoneNumberRecords();
	
	public List<T> getPhoneNumberRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPhoneNumberRecord(T phoneNumberRecord);
	
	public List<Long> addPhoneNumberRecords(Collection<T> phoneNumberRecords);
	
	public void savePhoneNumberRecord(T phoneNumberRecord);
	
	public void savePhoneNumberRecords(Collection<T> phoneNumberRecords);
	
	public void removeAllPhoneNumberRecords();
	
	public void removePhoneNumberRecord(T phoneNumberRecord);
	
	public void removePhoneNumberRecords(Collection<T> phoneNumberRecords);
	
}
