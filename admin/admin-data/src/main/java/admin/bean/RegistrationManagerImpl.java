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

import admin.Registration;
import admin.User;
import admin.dao.RegistrationDao;
import admin.dao.RegistrationDaoImpl;
import admin.entity.RegistrationEntity;
import admin.map.RegistrationMapper;
import admin.process.RegistrationImporter;


@Stateful
@Local(RegistrationManager.class)
@ConcurrencyManagement(BEAN)
public class RegistrationManagerImpl implements RegistrationManager {
	
	@PersistenceContext(unitName = "admin", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected RegistrationImporter registrationImporter;
	
	@Inject
	protected RegistrationMapper registrationMapper;
	
	@Inject
	@New(RegistrationDaoImpl.class)
	protected RegistrationDao<RegistrationEntity> registrationDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		registrationDao.initialize("Registration");
		registrationDao.setEntityManager(entityManager);
		registrationDao.setEntityClass(RegistrationEntity.class);
	}
	
	@Override
	public List<Registration> getAllRegistrationRecords() {
		List<RegistrationEntity> entityList = registrationDao.getAllRegistrationRecords();
		List<Registration> modelList = registrationMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Registration getRegistrationRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		RegistrationEntity entity = registrationDao.getRegistrationRecordById(id);
		Registration model = registrationMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Registration getRegistrationRecordByUser(User user) {
		RegistrationEntity entity = registrationDao.getRegistrationRecordByUser(user);
		Registration model = registrationMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Registration> getRegistrationRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<RegistrationEntity> entityList = registrationDao.getRegistrationRecordsByPage(pageIndex, pageSize);
		List<Registration> modelList = registrationMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addRegistrationRecord(Registration registration) {
		Assert.notNull(registration, "Registration record must be specified");
		RegistrationEntity entity = registrationMapper.toEntity(registration);
		Long id = registrationDao.addRegistrationRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public void saveRegistrationRecord(Registration registration) {
		Assert.notNull(registration, "Registration record must be specified");
		RegistrationEntity entity = registrationMapper.toEntity(registration);
		registrationDao.saveRegistrationRecord(entity);
	}
	
	@Override
	public void removeAllRegistrationRecords() {
		registrationDao.removeAllRegistrationRecords();
	}
	
	@Override
	public void removeRegistrationRecord(Registration registration) {
		Assert.notNull(registration, "Registration record must be specified");
		RegistrationEntity entity = registrationMapper.toEntity(registration);
		registrationDao.removeRegistrationRecord(entity);
	}
	
	@Override
	public void removeRegistrationRecord(Long id) {
		Assert.notNull(id, "Registration record ID must be specified");
		RegistrationEntity entity = registrationDao.getRegistrationRecordById(id);
		registrationDao.removeRegistrationRecord(entity);
	}
	
	@Override
	public void importRegistrationRecords() {
		registrationImporter.importRegistrationRecords();
	}
	
}
