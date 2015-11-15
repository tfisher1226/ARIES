package admin.bean;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.persistence.PersistenceContextType.EXTENDED;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;

import org.aries.Assert;
import org.aries.common.PersonName;

import admin.User;
import admin.UserCriteria;
import admin.dao.UserDao;
import admin.dao.UserDaoImpl;
import admin.entity.UserEntity;
import admin.map.UserMapper;
import admin.process.UserImporter;


@Stateful
@Local(UserManager.class)
@ConcurrencyManagement(BEAN)
public class UserManagerImpl implements UserManager {
	
	@PersistenceContext(unitName = "admin", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected UserImporter userImporter;
	
	@Inject
	protected UserMapper userMapper;
	
	@Inject
	@New(UserDaoImpl.class)
	protected UserDao<UserEntity> userDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		userDao.initialize("User");
		userDao.setEntityManager(entityManager);
		userDao.setEntityClass(UserEntity.class);
	}
	
	@Override
	public List<User> getAllUserRecords() {
		List<UserEntity> entityList = userDao.getAllUserRecords();
		List<User> modelList = userMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public User getUserRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		UserEntity entity = userDao.getUserRecordById(id);
		User model = userMapper.toModel(entity);
		return model;
	}
	
	@Override
	public User getUserRecordByUserName(String userName) {
		UserEntity entity = userDao.getUserRecordByUserName(userName);
		User model = userMapper.toModel(entity);
		return model;
	}
	
//	@Override
//	public List<User> getUserRecordsByName(PersonName name) {
//		Assert.notNull(name, "Name must be specified");
//		List<UserEntity> entityList = userDao.getUserRecordsByName(name);
//		List<User> modelList = userMapper.toModelList(entityList);
//		return modelList;
//	}
	
	@Override
	public List<User> getUserRecordsByPersonName(PersonName personName) {
		Assert.notNull(personName, "PersonName must be specified");
		List<UserEntity> entityList = userDao.getUserRecordsByPersonName(personName);
		List<User> modelList = userMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<User> getUserRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<UserEntity> entityList = userDao.getUserRecordsByPage(pageIndex, pageSize);
		List<User> modelList = userMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<User> getUserRecordsByCriteria(UserCriteria userCriteria) {
		Assert.notNull(userCriteria, "User record criteria must be specified");
		List<UserEntity> entityList = userDao.getUserRecordsByCriteria(userCriteria);
		List<User> modelList = userMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addUserRecord(User user) {
		Assert.notNull(user, "User record must be specified");
		UserEntity entity = userMapper.toEntity(user);
		Long id = userDao.addUserRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public void saveUserRecord(User user) {
		Assert.notNull(user, "User record must be specified");
		UserEntity entity = userMapper.toEntity(user);
		userDao.saveUserRecord(entity);
	}
	
	@Override
	public void removeAllUserRecords() {
		userDao.removeAllUserRecords();
	}
	
	@Override
	public void removeUserRecord(User user) {
		Assert.notNull(user, "User record must be specified");
		UserEntity entity = userMapper.toEntity(user);
		userDao.removeUserRecord(entity);
	}
	
	@Override
	public void removeUserRecord(Long id) {
		Assert.notNull(id, "User record ID must be specified");
		UserEntity entity = userDao.getUserRecordById(id);
		userDao.removeUserRecord(entity);
	}
	
	@Override
	public void removeUserRecords(UserCriteria userCriteria) {
		Assert.notNull(userCriteria, "User record criteria must be specified");
		userDao.removeUserRecords(userCriteria);
	}
	
	@Override
	public void importUserRecords() {
		userImporter.importUserRecords();
	}
	
}
