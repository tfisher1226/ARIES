package org.aries.common.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.entity.StreetAddressEntity;


public interface StreetAddressDao<T extends StreetAddressEntity> extends Dao<T> {
	
	public T getStreetAddressRecordById(Long id);
	
	public List<T> getAllStreetAddressRecords();
	
	public List<T> getStreetAddressRecordsByPage(int pageIndex, int pageSize);
	
	public Long addStreetAddressRecord(T streetAddressRecord);
	
	public List<Long> addStreetAddressRecords(Collection<T> streetAddressRecords);
	
	public void saveStreetAddressRecord(T streetAddressRecord);
	
	public void saveStreetAddressRecords(Collection<T> streetAddressRecords);
	
	public void removeAllStreetAddressRecords();
	
	public void removeStreetAddressRecord(T streetAddressRecord);
	
	public void removeStreetAddressRecords(Collection<T> streetAddressRecords);
	
}
