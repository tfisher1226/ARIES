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

import admin.Permission;
import admin.dao.PermissionDao;
import admin.dao.PermissionDaoImpl;
import admin.entity.PermissionEntity;
import admin.map.PermissionMapper;
import admin.process.PermissionImporter;


@Stateful
@Local(PermissionManager.class)
@ConcurrencyManagement(BEAN)
public class PermissionManagerImpl implements PermissionManager {
	
	@PersistenceContext(unitName = "admin", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected PermissionImporter permissionImporter;
	
	@Inject
	protected PermissionMapper permissionMapper;
	
	@Inject
	@New(PermissionDaoImpl.class)
	protected PermissionDao<PermissionEntity> permissionDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		permissionDao.initialize("Permission");
		permissionDao.setEntityManager(entityManager);
		permissionDao.setEntityClass(PermissionEntity.class);
	}
	
	@Override
	public List<Permission> getAllPermissionRecords() {
		List<PermissionEntity> entityList = permissionDao.getAllPermissionRecords();
		List<Permission> modelList = permissionMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Permission getPermissionRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		PermissionEntity entity = permissionDao.getPermissionRecordById(id);
		Permission model = permissionMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Permission> getPermissionRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<PermissionEntity> entityList = permissionDao.getPermissionRecordsByPage(pageIndex, pageSize);
		List<Permission> modelList = permissionMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public void importPermissionRecords() {
		permissionImporter.importPermissionRecords();
	}
	
}
