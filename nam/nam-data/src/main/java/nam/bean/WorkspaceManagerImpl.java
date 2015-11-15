package nam.bean;

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

import nam.dao.WorkspaceDao;
import nam.dao.WorkspaceDaoImpl;
import nam.entity.WorkspaceEntity;
import nam.map.WorkspaceMapper;
import nam.model.Workspace;
import nam.process.WorkspaceImporter;

import org.aries.Assert;


@Stateful
@Local(WorkspaceManager.class)
@ConcurrencyManagement(BEAN)
public class WorkspaceManagerImpl implements WorkspaceManager {
	
	@PersistenceContext(unitName = "nam", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected WorkspaceImporter workspaceImporter;
	
	@Inject
	protected WorkspaceMapper workspaceMapper;
	
	@Inject
	@New(WorkspaceDaoImpl.class)
	protected WorkspaceDao<WorkspaceEntity> workspaceDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		workspaceDao.initialize("Workspace");
		workspaceDao.setEntityManager(entityManager);
		workspaceDao.setEntityClass(WorkspaceEntity.class);
	}
	
	@Override
	public List<Workspace> getAllWorkspaceRecords() {
		List<WorkspaceEntity> entityList = workspaceDao.getAllWorkspaceRecords();
		List<Workspace> modelList = workspaceMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Workspace getWorkspaceRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		WorkspaceEntity entity = workspaceDao.getWorkspaceRecordById(id);
		Workspace model = workspaceMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Workspace getWorkspaceRecordByName(String name) {
		WorkspaceEntity entity = workspaceDao.getWorkspaceRecordByName(name);
		Workspace model = workspaceMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Workspace> getWorkspaceRecordsByUser(String user) {
		Assert.notNull(user, "User must be specified");
		List<WorkspaceEntity> entityList = workspaceDao.getWorkspaceRecordsByUser(user);
		List<Workspace> modelList = workspaceMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<Workspace> getWorkspaceRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<WorkspaceEntity> entityList = workspaceDao.getWorkspaceRecordsByPage(pageIndex, pageSize);
		List<Workspace> modelList = workspaceMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addWorkspaceRecord(Workspace workspace) {
		Assert.notNull(workspace, "Workspace record must be specified");
		WorkspaceEntity entity = workspaceMapper.toEntity(workspace);
		Long id = workspaceDao.addWorkspaceRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public void saveWorkspaceRecord(Workspace workspace) {
		Assert.notNull(workspace, "Workspace record must be specified");
		WorkspaceEntity entity = workspaceMapper.toEntity(workspace);
		workspaceDao.saveWorkspaceRecord(entity);
	}
	
	@Override
	public void removeAllWorkspaceRecords() {
		workspaceDao.removeAllWorkspaceRecords();
	}
	
	@Override
	public void removeWorkspaceRecord(Workspace workspace) {
		Assert.notNull(workspace, "Workspace record must be specified");
		WorkspaceEntity entity = workspaceMapper.toEntity(workspace);
		workspaceDao.removeWorkspaceRecord(entity);
	}
	
	@Override
	public void removeWorkspaceRecord(Long id) {
		Assert.notNull(id, "Workspace record ID must be specified");
		WorkspaceEntity entity = workspaceDao.getWorkspaceRecordById(id);
		workspaceDao.removeWorkspaceRecord(entity);
	}
	
	@Override
	public void importWorkspaceRecords() {
		workspaceImporter.importWorkspaceRecords();
	}
	
}
