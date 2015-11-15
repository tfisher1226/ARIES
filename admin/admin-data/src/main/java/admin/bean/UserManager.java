package admin.bean;

import java.util.List;

import org.aries.common.PersonName;

import admin.User;
import admin.UserCriteria;


public interface UserManager {
	
	public void initialize();
	
	public void clearContext();
	
	public List<User> getAllUserRecords();
	
	public User getUserRecordById(Long id);
	
	public User getUserRecordByUserName(String userName);
	
	//public List<User> getUserRecordsByName(PersonName name);
	
	public List<User> getUserRecordsByPersonName(PersonName personName);
	
	public List<User> getUserRecordsByPage(int pageIndex, int pageSize);
	
	public List<User> getUserRecordsByCriteria(UserCriteria userCriteria);
	
	public Long addUserRecord(User user);
	
	public void saveUserRecord(User user);
	
	public void removeAllUserRecords();
	
	public void removeUserRecord(User user);
	
	public void removeUserRecord(Long id);
	
	public void removeUserRecords(UserCriteria userCriteria);
	
	public void importUserRecords();
	
}
