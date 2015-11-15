package admin;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.inject.Inject;

import org.aries.Assert;
import org.aries.common.PersonName;
import org.aries.data.AbstractRepository;
import org.aries.util.ExceptionUtil;

import admin.bean.PermissionManager;
import admin.bean.PreferencesManager;
import admin.bean.RegistrationManager;
import admin.bean.RoleManager;
import admin.bean.SkinManager;
import admin.bean.UserManager;


@Stateful
@Local(AdminRepository.class)
public class AdminRepositoryImpl extends AbstractRepository implements AdminRepository {
	
	@Inject
	protected UserManager userManager;
	
	@Inject
	protected RoleManager roleManager;
	
	@Inject
	protected PermissionManager permissionManager;
	
	@Inject
	protected PreferencesManager preferencesManager;
	
	@Inject
	protected RegistrationManager registrationManager;
	
	@Inject
	protected SkinManager skinManager;
	
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public RoleManager getRoleManager() {
		return roleManager;
	}
	
	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	public PermissionManager getPermissionManager() {
		return permissionManager;
	}
	
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
	
	public PreferencesManager getPreferencesManager() {
		return preferencesManager;
	}
	
	public void setPreferencesManager(PreferencesManager preferencesManager) {
		this.preferencesManager = preferencesManager;
	}
	
	public RegistrationManager getRegistrationManager() {
		return registrationManager;
	}
	
	public void setRegistrationManager(RegistrationManager registrationManager) {
		this.registrationManager = registrationManager;
	}
	
	public SkinManager getSkinManager() {
		return skinManager;
	}
	
	public void setSkinManager(SkinManager skinManager) {
		this.skinManager = skinManager;
	}
	
	@Override
	public void clearContext() {
		try {
			userManager.clearContext();
			roleManager.clearContext();
			permissionManager.clearContext();
			preferencesManager.clearContext();
			registrationManager.clearContext();
			skinManager.clearContext();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<User> getAllUserRecords() {
		try {
			List<User> records = userManager.getAllUserRecords();
			Assert.notNull(records, "User record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public User getUserRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			User record = userManager.getUserRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public User getUserRecordByUserName(String userName) {
		try {
			Assert.notNull(userName, "UserName must be specified");
			User record = userManager.getUserRecordByUserName(userName);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
//	@Override
//	public List<User> getUserRecordsByName(PersonName name) {
//		try {
//			Assert.notNull(name, "Name must be specified");
//			List<User> records = userManager.getUserRecordsByName(name);
//			Assert.notNull(records, "User record list null");
//			return records;
//			
//		} catch (Exception e) {
//			throw ExceptionUtil.createRuntimeException(e);
//		}
//	}
	
	@Override
	public List<User> getUserRecordsByPersonName(PersonName personName) {
		try {
			Assert.notNull(personName, "PersonName must be specified");
			List<User> records = userManager.getUserRecordsByPersonName(personName);
			Assert.notNull(records, "User record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<User> getUserRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<User> records = userManager.getUserRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "User record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<User> getUserRecordsByCriteria(UserCriteria userCriteria) {
		try {
			Assert.notNull(userCriteria, "User record criteria must be specified");
			List<User> records = userManager.getUserRecordsByCriteria(userCriteria);
			Assert.notNull(records, "User record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addUserRecord(User user) {
		try {
			Assert.notNull(user, "User record must be specified");
			Long id = userManager.addUserRecord(user);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveUserRecord(User user) {
		try {
			Assert.notNull(user, "User record must be specified");
			userManager.saveUserRecord(user);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllUserRecords() {
		try {
			userManager.removeAllUserRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeUserRecord(User user) {
		try {
			Assert.notNull(user, "User record must be specified");
			userManager.removeUserRecord(user);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeUserRecord(Long id) {
		try {
			Assert.notNull(id, "User record ID must be specified");
			userManager.removeUserRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeUserRecords(UserCriteria userCriteria) {
		try {
			Assert.notNull(userCriteria, "User record criteria must be specified");
			userManager.removeUserRecords(userCriteria);
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importUserRecords() {
		try {
			userManager.importUserRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Role> getAllRoleRecords() {
		try {
			List<Role> records = roleManager.getAllRoleRecords();
			Assert.notNull(records, "Role record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Role getRoleRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Role record = roleManager.getRoleRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Role getRoleRecordByName(String name) {
		try {
			Assert.notNull(name, "Name must be specified");
			Role record = roleManager.getRoleRecordByName(name);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Role> getRoleRecordsByRoleType(RoleType roleType) {
		try {
			Assert.notNull(roleType, "RoleType must be specified");
			List<Role> records = roleManager.getRoleRecordsByRoleType(roleType);
			Assert.notNull(records, "Role record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Role> getRoleRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Role> records = roleManager.getRoleRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Role record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addRoleRecord(Role role) {
		try {
			Assert.notNull(role, "Role record must be specified");
			Long id = roleManager.addRoleRecord(role);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveRoleRecord(Role role) {
		try {
			Assert.notNull(role, "Role record must be specified");
			roleManager.saveRoleRecord(role);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllRoleRecords() {
		try {
			roleManager.removeAllRoleRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeRoleRecord(Role role) {
		try {
			Assert.notNull(role, "Role record must be specified");
			roleManager.removeRoleRecord(role);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeRoleRecord(Long id) {
		try {
			Assert.notNull(id, "Role record ID must be specified");
			roleManager.removeRoleRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importRoleRecords() {
		try {
			roleManager.importRoleRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Permission> getAllPermissionRecords() {
		try {
			List<Permission> records = permissionManager.getAllPermissionRecords();
			Assert.notNull(records, "Permission record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Permission getPermissionRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Permission record = permissionManager.getPermissionRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Permission> getPermissionRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Permission> records = permissionManager.getPermissionRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Permission record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importPermissionRecords() {
		try {
			permissionManager.importPermissionRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Preferences> getAllPreferencesRecords() {
		try {
			List<Preferences> records = preferencesManager.getAllPreferencesRecords();
			Assert.notNull(records, "Preferences record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Preferences getPreferencesRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Preferences record = preferencesManager.getPreferencesRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Preferences getPreferencesRecordByUser(User user) {
		try {
			Assert.notNull(user, "User must be specified");
			Preferences record = preferencesManager.getPreferencesRecordByUser(user);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Preferences> getPreferencesRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Preferences> records = preferencesManager.getPreferencesRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Preferences record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importPreferencesRecords() {
		try {
			preferencesManager.importPreferencesRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Registration> getAllRegistrationRecords() {
		try {
			List<Registration> records = registrationManager.getAllRegistrationRecords();
			Assert.notNull(records, "Registration record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Registration getRegistrationRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Registration record = registrationManager.getRegistrationRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Registration getRegistrationRecordByUser(User user) {
		try {
			Assert.notNull(user, "User must be specified");
			Registration record = registrationManager.getRegistrationRecordByUser(user);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Registration> getRegistrationRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Registration> records = registrationManager.getRegistrationRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Registration record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addRegistrationRecord(Registration registration) {
		try {
			Assert.notNull(registration, "Registration record must be specified");
			Long id = registrationManager.addRegistrationRecord(registration);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveRegistrationRecord(Registration registration) {
		try {
			Assert.notNull(registration, "Registration record must be specified");
			registrationManager.saveRegistrationRecord(registration);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllRegistrationRecords() {
		try {
			registrationManager.removeAllRegistrationRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeRegistrationRecord(Registration registration) {
		try {
			Assert.notNull(registration, "Registration record must be specified");
			registrationManager.removeRegistrationRecord(registration);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeRegistrationRecord(Long id) {
		try {
			Assert.notNull(id, "Registration record ID must be specified");
			registrationManager.removeRegistrationRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importRegistrationRecords() {
		try {
			registrationManager.importRegistrationRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Skin> getAllSkinRecords() {
		try {
			List<Skin> records = skinManager.getAllSkinRecords();
			Assert.notNull(records, "Skin record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Skin getSkinRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Skin record = skinManager.getSkinRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Skin getSkinRecordByName(String name) {
		try {
			Assert.notNull(name, "Name must be specified");
			Skin record = skinManager.getSkinRecordByName(name);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Skin> getSkinRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Skin> records = skinManager.getSkinRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Skin record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addSkinRecord(Skin skin) {
		try {
			Assert.notNull(skin, "Skin record must be specified");
			Long id = skinManager.addSkinRecord(skin);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveSkinRecord(Skin skin) {
		try {
			Assert.notNull(skin, "Skin record must be specified");
			skinManager.saveSkinRecord(skin);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllSkinRecords() {
		try {
			skinManager.removeAllSkinRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeSkinRecord(Skin skin) {
		try {
			Assert.notNull(skin, "Skin record must be specified");
			skinManager.removeSkinRecord(skin);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeSkinRecord(Long id) {
		try {
			Assert.notNull(id, "Skin record ID must be specified");
			skinManager.removeSkinRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importSkinRecords() {
		try {
			skinManager.importSkinRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
}
