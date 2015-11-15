package admin;

import java.util.List;

import org.aries.common.PersonName;


public interface AdminRepository {
	
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
	
	public List<Role> getAllRoleRecords();
	
	public Role getRoleRecordById(Long id);
	
	public Role getRoleRecordByName(String name);
	
	public List<Role> getRoleRecordsByRoleType(RoleType roleType);
	
	public List<Role> getRoleRecordsByPage(int pageIndex, int pageSize);
	
	public Long addRoleRecord(Role role);
	
	public void saveRoleRecord(Role role);
	
	public void removeAllRoleRecords();
	
	public void removeRoleRecord(Role role);
	
	public void removeRoleRecord(Long id);
	
	public void importRoleRecords();
	
	public List<Permission> getAllPermissionRecords();
	
	public Permission getPermissionRecordById(Long id);
	
	public List<Permission> getPermissionRecordsByPage(int pageIndex, int pageSize);
	
	public void importPermissionRecords();
	
	public List<Preferences> getAllPreferencesRecords();
	
	public Preferences getPreferencesRecordById(Long id);
	
	public Preferences getPreferencesRecordByUser(User user);
	
	public List<Preferences> getPreferencesRecordsByPage(int pageIndex, int pageSize);
	
	public void importPreferencesRecords();
	
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
	
	public List<Skin> getAllSkinRecords();
	
	public Skin getSkinRecordById(Long id);
	
	public Skin getSkinRecordByName(String name);
	
	public List<Skin> getSkinRecordsByPage(int pageIndex, int pageSize);
	
	public Long addSkinRecord(Skin skin);
	
	public void saveSkinRecord(Skin skin);
	
	public void removeAllSkinRecords();
	
	public void removeSkinRecord(Skin skin);
	
	public void removeSkinRecord(Long id);
	
	public void importSkinRecords();
	
}
