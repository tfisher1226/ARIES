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

import admin.Preferences;
import admin.User;
import admin.dao.PreferencesDao;
import admin.dao.PreferencesDaoImpl;
import admin.entity.PreferencesEntity;
import admin.map.PreferencesMapper;
import admin.process.PreferencesImporter;


@Stateful
@Local(PreferencesManager.class)
@ConcurrencyManagement(BEAN)
public class PreferencesManagerImpl implements PreferencesManager {
	
	@PersistenceContext(unitName = "admin", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected PreferencesImporter preferencesImporter;
	
	@Inject
	protected PreferencesMapper preferencesMapper;
	
	@Inject
	@New(PreferencesDaoImpl.class)
	protected PreferencesDao<PreferencesEntity> preferencesDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		preferencesDao.initialize("Preferences");
		preferencesDao.setEntityManager(entityManager);
		preferencesDao.setEntityClass(PreferencesEntity.class);
	}
	
	@Override
	public List<Preferences> getAllPreferencesRecords() {
		List<PreferencesEntity> entityList = preferencesDao.getAllPreferencesRecords();
		List<Preferences> modelList = preferencesMapper.toModelList(null, entityList);
		return modelList;
	}
	
	@Override
	public Preferences getPreferencesRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		PreferencesEntity entity = preferencesDao.getPreferencesRecordById(id);
		Preferences model = preferencesMapper.toModel(null, entity);
		return model;
	}
	
	@Override
	public Preferences getPreferencesRecordByUser(User user) {
		PreferencesEntity entity = preferencesDao.getPreferencesRecordByUser(user);
		Preferences model = preferencesMapper.toModel(null, entity);
		return model;
	}
	
	@Override
	public List<Preferences> getPreferencesRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<PreferencesEntity> entityList = preferencesDao.getPreferencesRecordsByPage(pageIndex, pageSize);
		List<Preferences> modelList = preferencesMapper.toModelList(null, entityList);
		return modelList;
	}
	
	@Override
	public void importPreferencesRecords() {
		preferencesImporter.importPreferencesRecords();
	}
	
}
