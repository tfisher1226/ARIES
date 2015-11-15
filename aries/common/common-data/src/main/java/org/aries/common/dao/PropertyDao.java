package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.PropertyEntity;


public interface PropertyDao<T extends PropertyEntity> extends Dao<T> {
	
	public T getPropertyRecordById(Long id);
	
	public List<T> getAllPropertyRecords();
	
	public List<T> getPropertyRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPropertyRecord(T propertyRecord);
	
	public List<Long> addPropertyRecords(Collection<T> propertyRecords);
	
	public void savePropertyRecord(T propertyRecord);
	
	public void savePropertyRecords(Collection<T> propertyRecords);
	
	public void removeAllPropertyRecords();
	
	public void removePropertyRecord(T propertyRecord);
	
	public void removePropertyRecords(Collection<T> propertyRecords);
	
}
