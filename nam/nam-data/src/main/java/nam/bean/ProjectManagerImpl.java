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

import nam.dao.ProjectDao;
import nam.dao.ProjectDaoImpl;
import nam.entity.ProjectEntity;
import nam.map.ProjectMapper;
import nam.model.Project;
import nam.process.ProjectImporter;

import org.aries.Assert;


@Stateful
@Local(ProjectManager.class)
@ConcurrencyManagement(BEAN)
public class ProjectManagerImpl implements ProjectManager {
	
	@PersistenceContext(unitName = "nam", type = EXTENDED)
	protected EntityManager entityManager;
	
	@Inject
	protected ProjectImporter projectImporter;
	
	@Inject
	protected ProjectMapper projectMapper;
	
	@Inject
	@New(ProjectDaoImpl.class)
	protected ProjectDao<ProjectEntity> projectDao;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	public void clearContext() {
		entityManager.clear();
	}
	
	@PostConstruct
	public void initialize() {
		projectDao.initialize("Project");
		projectDao.setEntityManager(entityManager);
		projectDao.setEntityClass(ProjectEntity.class);
	}
	
	@Override
	public List<Project> getAllProjectRecords() {
		List<ProjectEntity> entityList = projectDao.getAllProjectRecords();
		List<Project> modelList = projectMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Project getProjectRecordById(Long id) {
		Assert.notNull(id, "ID must be specified");
		ProjectEntity entity = projectDao.getProjectRecordById(id);
		Project model = projectMapper.toModel(entity);
		return model;
	}
	
	@Override
	public Project getProjectRecordByName(String name) {
		ProjectEntity entity = projectDao.getProjectRecordByName(name);
		Project model = projectMapper.toModel(entity);
		return model;
	}
	
	@Override
	public List<Project> getProjectRecordsByOwner(String owner) {
		Assert.notNull(owner, "Owner must be specified");
		List<ProjectEntity> entityList = projectDao.getProjectRecordsByOwner(owner);
		List<Project> modelList = projectMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public List<Project> getProjectRecordsByPage(int pageIndex, int pageSize) {
		Assert.notNull(pageIndex, "Page-index must be specified");
		Assert.notNull(pageSize, "Page-size must be specified");
		List<ProjectEntity> entityList = projectDao.getProjectRecordsByPage(pageIndex, pageSize);
		List<Project> modelList = projectMapper.toModelList(entityList);
		return modelList;
	}
	
	@Override
	public Long addProjectRecord(Project project) {
		Assert.notNull(project, "Project record must be specified");
		ProjectEntity entity = projectMapper.toEntity(project);
		Long id = projectDao.addProjectRecord(entity);
		Assert.notNull(id, "Id must exist");
		return id;
	}
	
	@Override
	public void saveProjectRecord(Project project) {
		Assert.notNull(project, "Project record must be specified");
		ProjectEntity entity = projectMapper.toEntity(project);
		projectDao.saveProjectRecord(entity);
	}
	
	@Override
	public void removeAllProjectRecords() {
		projectDao.removeAllProjectRecords();
	}
	
	@Override
	public void removeProjectRecord(Project project) {
		Assert.notNull(project, "Project record must be specified");
		ProjectEntity entity = projectMapper.toEntity(project);
		projectDao.removeProjectRecord(entity);
	}
	
	@Override
	public void removeProjectRecord(Long id) {
		Assert.notNull(id, "Project record ID must be specified");
		ProjectEntity entity = projectDao.getProjectRecordById(id);
		projectDao.removeProjectRecord(entity);
	}
	
	@Override
	public void importProjectRecords() {
		projectImporter.importProjectRecords();
	}
	
}
