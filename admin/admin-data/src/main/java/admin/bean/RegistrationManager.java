package admin.bean;

import java.util.List;

import admin.Registration;
import admin.User;


public interface RegistrationManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<Registration> getAllRegistrationRecords();
	
	public Registration getRegistrationRecordById(Long id);
	
	public Registration getRegistrationRecordByUser(User user);
	
	public List<Registration> getRegistrationRecordsByPage(int pageIndex, int pageSize);
	
	public Long addRegistrationRecord(Registration registration);
	
	public void saveRegistrationRecord(Registration registration);
	
	public void removeAllRegistrationRecords();
	
	public void removeRegistrationRecord(Registration registration);
	
	public void removeRegistrationRecord(Long id);
	
	public void importRegistrationRecords();
	
}
