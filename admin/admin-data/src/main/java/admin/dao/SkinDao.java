package admin.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import admin.entity.SkinEntity;


public interface SkinDao<T extends SkinEntity> extends Dao<T> {
	
	public T getSkinRecordById(Long id);
	
	public T getSkinRecordByName(String name);
	
	public List<T> getAllSkinRecords();
	
	public List<T> getSkinRecordsByPage(int pageIndex, int pageSize);
	
	public Long addSkinRecord(T skinRecord);
	
	public List<Long> addSkinRecords(Collection<T> skinRecords);
	
	public void saveSkinRecord(T skinRecord);
	
	public void saveSkinRecords(Collection<T> skinRecords);
	
	public void removeAllSkinRecords();
	
	public void removeSkinRecord(T skinRecord);
	
	public void removeSkinRecords(Collection<T> skinRecords);
	
}
