package admin.dao;

import javax.persistence.EntityManager;

import admin.Permission;
import admin.Preferences;
import admin.Registration;
import admin.Role;
import admin.Skin;
import admin.User;
import admin.entity.PermissionEntity;
import admin.entity.PreferencesEntity;
import admin.entity.RegistrationEntity;
import admin.entity.RoleEntity;
import admin.entity.SkinEntity;
import admin.entity.UserEntity;
import admin.map.AdminMapperFixture;
import admin.util.AdminFixture;


public class AdminDaoITHelper {

	private PermissionDao<PermissionEntity> permissionDao;

	private PreferencesDao<PreferencesEntity> preferencesDao;

	private RegistrationDao<RegistrationEntity> registrationDao;

	private RoleDao<RoleEntity> roleDao;

	private SkinDao<SkinEntity> skinDao;

	private UserDao<UserEntity> userDao;


	public AdminDaoITHelper(EntityManager entityManager) {
		permissionDao = createPermissionDao(entityManager);
		preferencesDao = createPreferencesDao(entityManager);
		registrationDao = createRegistrationDao(entityManager);
		roleDao = createRoleDao(entityManager);
		skinDao = createSkinDao(entityManager);
		userDao = createUserDao(entityManager);
	}


	public PermissionDao createPermissionDao(EntityManager entityManager) {
		PermissionDaoImpl permissionDao = new PermissionDaoImpl();
		permissionDao.setEntityManager(entityManager);
		return permissionDao;
	}

	public PreferencesDao createPreferencesDao(EntityManager entityManager) {
		PreferencesDaoImpl preferencesDao = new PreferencesDaoImpl();
		preferencesDao.setEntityManager(entityManager);
		return preferencesDao;
	}

	public RegistrationDao createRegistrationDao(EntityManager entityManager) {
		RegistrationDaoImpl registrationDao = new RegistrationDaoImpl();
		registrationDao.setEntityManager(entityManager);
		return registrationDao;
	}
	
	public RoleDao createRoleDao(EntityManager entityManager) {
		RoleDaoImpl roleDao = new RoleDaoImpl();
		roleDao.setEntityManager(entityManager);
		return roleDao;
	}

	public SkinDao createSkinDao(EntityManager entityManager) {
		SkinDaoImpl skinDao = new SkinDaoImpl();
		skinDao.setEntityManager(entityManager);
		return skinDao;
	}

	public UserDao createUserDao(EntityManager entityManager) {
		UserDaoImpl userDao = new UserDaoImpl();
		userDao.setEntityManager(entityManager);
		return userDao;
	}

	public PermissionEntity createPermissionEntity() throws Exception {
		Permission permission = AdminFixture.createPermission();
		PermissionEntity permissionEntity = AdminMapperFixture.getPermissionMapper().toEntity(permission);
		return permissionEntity;
	}

	public PreferencesEntity createPreferencesEntity() throws Exception {
		PreferencesEntity preferencesEntity = createPreferencesEntity(createUserEntity());
		return preferencesEntity;
	}

	public PreferencesEntity createPreferencesEntity(UserEntity userEntity) throws Exception {
		Preferences preferences = AdminFixture.createPreferences(null);
		PreferencesEntity preferencesEntity = AdminMapperFixture.getPreferencesMapper().toEntity(userEntity, preferences);
		preferencesEntity.setUser(createAndPersistUserEntity());
		userEntity.setPreferences(preferencesEntity);
		return preferencesEntity;
	}

	public RegistrationEntity createRegistrationEntity() throws Exception {
		Registration registration = AdminFixture.createRegistration();
		RegistrationEntity registrationEntity = AdminMapperFixture.getRegistrationMapper().toEntity(registration);
		registrationEntity.setUser(createAndPersistUserEntity());
		return registrationEntity;
	}
	
	public RoleEntity createRoleEntity() throws Exception {
		Role role = AdminFixture.createRole();
		RoleEntity roleEntity = AdminMapperFixture.getRoleMapper().toEntity(role);
		roleEntity.getGroups().add(createAndPersistRoleEntity());
		roleEntity.getPermissions().add(createAndPersistPermissionEntity());
		return roleEntity;
	}

	public SkinEntity createSkinEntity() throws Exception {
		Skin skin = AdminFixture.createSkin();
		SkinEntity skinEntity = AdminMapperFixture.getSkinMapper().toEntity(skin);
		return skinEntity;
	}

	public UserEntity createUserEntity() throws Exception {
		User user = AdminFixture.createUser();
		UserEntity userEntity = AdminMapperFixture.getUserMapper().toEntity(user);
		userEntity.getRoles().add(createAndPersistRoleEntity());
		return userEntity;
	}

	public PermissionEntity createAndPersistPermissionEntity() throws Exception {
		PermissionEntity permissionEntity = createPermissionEntity();
		Long id = persistPermissionEntity(permissionEntity);
		permissionEntity.setId(id);
		return permissionEntity;
	}

	public Long persistPermissionEntity(PermissionEntity permissionEntity) throws Exception {
		Long id = permissionDao.addPermissionRecord(permissionEntity);
		return id;
	}

	public PreferencesEntity createAndPersistPreferencesEntity() throws Exception {
		PreferencesEntity preferencesEntity = createPreferencesEntity();
		Long id = persistPreferencesEntity(preferencesEntity);
		preferencesEntity.setId(id);
		return preferencesEntity;
	}

	public Long persistPreferencesEntity(PreferencesEntity preferencesEntity) throws Exception {
		Long id = preferencesDao.addPreferencesRecord(preferencesEntity);
		return id;
	}
	
	public RegistrationEntity createAndPersistRegistrationEntity() throws Exception {
		RegistrationEntity registrationEntity = createRegistrationEntity();
		Long id = persistRegistrationEntity(registrationEntity);
		registrationEntity.setId(id);
		return registrationEntity;
	}
	
	public Long persistRegistrationEntity(RegistrationEntity registrationEntity) throws Exception {
		Long id = registrationDao.addRegistrationRecord(registrationEntity);
		return id;
	}

	public RoleEntity createAndPersistRoleEntity() throws Exception {
		RoleEntity roleEntity = createRoleEntity();
		Long id = persistRoleEntity(roleEntity);
		roleEntity.setId(id);
		return roleEntity;
	}

	public Long persistRoleEntity(RoleEntity roleEntity) throws Exception {
		Long id = roleDao.addRoleRecord(roleEntity);
		return id;
	}

	public SkinEntity createAndPersistSkinEntity() throws Exception {
		SkinEntity skinEntity = createSkinEntity();
		Long id = persistSkinEntity(skinEntity);
		skinEntity.setId(id);
		return skinEntity;
	}

	public Long persistSkinEntity(SkinEntity skinEntity) throws Exception {
		Long id = skinDao.addSkinRecord(skinEntity);
		return id;
	}

	public UserEntity createAndPersistUserEntity() throws Exception {
		UserEntity userEntity = createUserEntity();
		Long id = persistUserEntity(userEntity);
		userEntity.setId(id);
		return userEntity;
	}

	public Long persistUserEntity(UserEntity userEntity) throws Exception {
		Long id = userDao.addUserRecord(userEntity);
		return id;
	}

}
