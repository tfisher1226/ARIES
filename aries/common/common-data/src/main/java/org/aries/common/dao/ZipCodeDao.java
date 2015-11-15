package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.ZipCodeEntity;


public interface ZipCodeDao<T extends ZipCodeEntity> extends Dao<T> {
	
	public T getZipCodeRecordById(Long id);
	
	public List<T> getAllZipCodeRecords();
	
	public List<T> getZipCodeRecordsByPage(int pageIndex, int pageSize);
	
	public Long addZipCodeRecord(T zipCodeRecord);
	
	public List<Long> addZipCodeRecords(Collection<T> zipCodeRecords);
	
	public void saveZipCodeRecord(T zipCodeRecord);
	
	public void saveZipCodeRecords(Collection<T> zipCodeRecords);
	
	public void removeAllZipCodeRecords();
	
	public void removeZipCodeRecord(T zipCodeRecord);
	
	public void removeZipCodeRecords(Collection<T> zipCodeRecords);
	
}
