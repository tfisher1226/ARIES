package redhat.jee_migration_example.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import redhat.jee_migration_example.entity.AbstractItemEntity;


public interface ItemDao<T extends AbstractItemEntity> extends Dao<T> {
	
	public T getItemRecordById(Long id);
	
	public T getItemRecordByKey(String key);
	
	public T getItemRecordByName(String name);

	public List<T> getAllItemRecords();

	public List<T> getItemRecordsByPage(int pageIndex, int pageSize);
	
	public Long addItemRecord(T itemRecord);
	
	public List<Long> addItemRecords(Collection<T> itemRecords);
	
	public void saveItemRecord(T itemRecord);
	
	public void saveItemRecords(Collection<T> itemRecords);
	
	public void removeAllItemRecords();
	
	public void removeItemRecord(T itemRecord);
	
	public void removeItemRecords(Collection<T> itemRecords);
	
	public void removeItemRecordByKey(String key);
	
}
