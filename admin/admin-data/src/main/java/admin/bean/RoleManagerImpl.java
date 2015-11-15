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

import admin.Role;
import admin.RoleType;
import admin.dao.RoleDao;
import admin.dao.RoleDaoImpl;
import admin.entity.RoleEntity;
import admin.map.RoleMapper;
import admin.process.RoleImporter;


@Stateful
@Local(RoleManager.class)
@ConcurrencyManagement(BEAN)
public class RoleManagerImpl implements RoleManager {
	
	@PersistenceContext(unitName = "admin", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected RoleImporter roleImporter;
	
	@Inject
	protected RoleMapper roleMapper;
	
	@Inject
	@New(RoleDaoImpl.class)
	protected RoleDao<RoleEntity> roleDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		roleDao.initialize("Role");
		roleDao.setEntityManager(entityManager);
		roleDao.setEntityClass(RoleEntity.class);
	}
	
	@Override
	public List<Role> getAllRoleRecords() {
		List<RoleEntity> entityList = roleDao.getAllRoleRecords();
		List<Role> modelList = roleMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Role getRoleRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		RoleEntity entity = roleDao.getRoleRecordById(id);
		Role model = roleMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Role getRoleRecordByName(String name) {
		RoleEntity entity = roleDao.getRoleRecordByName(name);
		Role model = roleMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Role> getRoleRecordsByRoleType(RoleType roleType) {
		Assert.notNull(roleType, "RoleType must be specified");
		List<RoleEntity> entityList = roleDao.getRoleRecordsByRoleType(roleType);
		List<Role> modelList = roleMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<Role> getRoleRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<RoleEntity> entityList = roleDao.getRoleRecordsByPage(pageIndex, pageSize);
		List<Role> modelList = roleMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addRoleRecord(Role role) {
		Assert.notNull(role, "Role record must be specified");
		RoleEntity entity = roleMapper.toEntity(role);
		Long id = roleDao.addRoleRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public void saveRoleRecord(Role role) {
		Assert.notNull(role, "Role record must be specified");
		RoleEntity entity = roleMapper.toEntity(role);
		roleDao.saveRoleRecord(entity);
	}
	
	@Override
	public void removeAllRoleRecords() {
		roleDao.removeAllRoleRecords();
	}
	
	@Override
	public void removeRoleRecord(Role role) {
		Assert.notNull(role, "Role record must be specified");
		RoleEntity entity = roleMapper.toEntity(role);
		roleDao.removeRoleRecord(entity);
	}
	
	@Override
	public void removeRoleRecord(Long id) {
		Assert.notNull(id, "Role record ID must be specified");
		RoleEntity entity = roleDao.getRoleRecordById(id);
		roleDao.removeRoleRecord(entity);
	}
	
	@Override
	public void importRoleRecords() {
		roleImporter.importRoleRecords();
	}
	
}
