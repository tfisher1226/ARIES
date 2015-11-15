package admin.bean;

import java.util.List;

import admin.Preferences;
import admin.User;


public interface PreferencesManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Preferences> getAllPreferencesRecords();
	
	public Preferences getPreferencesRecordById(Long id);
	
	public Preferences getPreferencesRecordByUser(User user);
	
	public List<Preferences> getPreferencesRecordsByPage(int pageIndex, int pageSize);
	
	public void importPreferencesRecords();
	
}
