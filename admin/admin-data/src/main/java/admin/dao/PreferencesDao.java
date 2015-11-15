package admin.dao;

import java.util.Collection;
import java.util.List;

import org.aries.common.dao.Dao;

import admin.User;
import admin.entity.PreferencesEntity;


public interface PreferencesDao<T extends PreferencesEntity> extends Dao<T> {
	
	public T getPreferencesRecordById(Long id);
	
	public T getPreferencesRecordByUser(User user);
	
	public List<T> getAllPreferencesRecords();
	
	public List<T> getPreferencesRecordsByPage(int pageIndex, int pageSize);
	
	public Long addPreferencesRecord(T preferencesRecord);
	
	public List<Long> addPreferencesRecords(Collection<T> preferencesRecords);
	
	public void savePreferencesRecord(T preferencesRecord);
	
	public void savePreferencesRecords(Collection<T> preferencesRecords);
	
	public void removeAllPreferencesRecords();
	
	public void removePreferencesRecord(T preferencesRecord);
	
	public void removePreferencesRecords(Collection<T> preferencesRecords);
	
}
