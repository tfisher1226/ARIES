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

import admin.Skin;
import admin.dao.SkinDao;
import admin.dao.SkinDaoImpl;
import admin.entity.SkinEntity;
import admin.map.SkinMapper;
import admin.process.SkinImporter;


@Stateful
@Local(SkinManager.class)
@ConcurrencyManagement(BEAN)
public class SkinManagerImpl implements SkinManager {
	
	@PersistenceContext(unitName = "admin", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected SkinImporter skinImporter;
	
	@Inject
	protected SkinMapper skinMapper;
	
	@Inject
	@New(SkinDaoImpl.class)
	protected SkinDao<SkinEntity> skinDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		skinDao.initialize("Skin");
		skinDao.setEntityManager(entityManager);
		skinDao.setEntityClass(SkinEntity.class);
	}
	
	@Override
	public List<Skin> getAllSkinRecords() {
		List<SkinEntity> entityList = skinDao.getAllSkinRecords();
		List<Skin> modelList = skinMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Skin getSkinRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		SkinEntity entity = skinDao.getSkinRecordById(id);
		Skin model = skinMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Skin getSkinRecordByName(String name) {
		SkinEntity entity = skinDao.getSkinRecordByName(name);
		Skin model = skinMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Skin> getSkinRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<SkinEntity> entityList = skinDao.getSkinRecordsByPage(pageIndex, pageSize);
		List<Skin> modelList = skinMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addSkinRecord(Skin skin) {
		Assert.notNull(skin, "Skin record must be specified");
		SkinEntity entity = skinMapper.toEntity(skin);
		Long id = skinDao.addSkinRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public void saveSkinRecord(Skin skin) {
		Assert.notNull(skin, "Skin record must be specified");
		SkinEntity entity = skinMapper.toEntity(skin);
		skinDao.saveSkinRecord(entity);
	}
	
	@Override
	public void removeAllSkinRecords() {
		skinDao.removeAllSkinRecords();
	}
	
	@Override
	public void removeSkinRecord(Skin skin) {
		Assert.notNull(skin, "Skin record must be specified");
		SkinEntity entity = skinMapper.toEntity(skin);
		skinDao.removeSkinRecord(entity);
	}
	
	@Override
	public void removeSkinRecord(Long id) {
		Assert.notNull(id, "Skin record ID must be specified");
		SkinEntity entity = skinDao.getSkinRecordById(id);
		skinDao.removeSkinRecord(entity);
	}
	
	@Override
	public void importSkinRecords() {
		skinImporter.importSkinRecords();
	}
	
}
