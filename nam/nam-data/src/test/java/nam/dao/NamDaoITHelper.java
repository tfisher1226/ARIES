package nam.dao;

import javax.persistence.EntityManager;

import nam.entity.FileEntity;
import nam.entity.ProjectEntity;
import nam.entity.WorkspaceEntity;
import nam.map.NamMapperFixture;
import nam.model.File;
import nam.model.Project;
import nam.model.Workspace;
import nam.model.util.ModelFixture;


public class NamDaoITHelper {

	private FileDao<FileEntity> fileDao;

	private ProjectDao<ProjectEntity> projectDao;

	private WorkspaceDao<WorkspaceEntity> workspaceDao;


	public NamDaoITHelper(EntityManager entityManager) {
		fileDao = createFileDao(entityManager);
		projectDao = createProjectDao(entityManager);
		workspaceDao = createWorkspaceDao(entityManager);
	}


	public FileDao<FileEntity> createFileDao(EntityManager entityManager) {
		FileDaoImpl<FileEntity> fileDao = new FileDaoImpl<FileEntity>();
		fileDao.setEntityManager(entityManager);
		return fileDao;
	}

	public ProjectDao<ProjectEntity> createProjectDao(EntityManager entityManager) {
		ProjectDaoImpl<ProjectEntity> projectDao = new ProjectDaoImpl<ProjectEntity>();
		projectDao.setEntityManager(entityManager);
		return projectDao;
	}

	public WorkspaceDao<WorkspaceEntity> createWorkspaceDao(EntityManager entityManager) {
		WorkspaceDaoImpl<WorkspaceEntity> workspaceDao = new WorkspaceDaoImpl<WorkspaceEntity>();
		workspaceDao.setEntityManager(entityManager);
		return workspaceDao;
	}

	public FileEntity createFileEntity() throws Exception {
		File file = ModelFixture.createFile();
		FileEntity fileEntity = NamMapperFixture.getFileMapper().toEntity(file);
		return fileEntity;
	}

	public ProjectEntity createProjectEntity() throws Exception {
		Project project = ModelFixture.createProject();
		ProjectEntity projectEntity = NamMapperFixture.getProjectMapper().toEntity(project);
		projectEntity.getFiles().add(createAndPersistFileEntity());
		return projectEntity;
	}

	public WorkspaceEntity createWorkspaceEntity() throws Exception {
		Workspace workspace = ModelFixture.createWorkspace();
		WorkspaceEntity workspaceEntity = NamMapperFixture.getWorkspaceMapper().toEntity(workspace);
		//TODO workspaceEntity.getProjects().add(createAndPersistProjectEntity());
		return workspaceEntity;
	}

	public FileEntity createAndPersistFileEntity() throws Exception {
		FileEntity fileEntity = createFileEntity();
		Long id = persistFileEntity(fileEntity);
		fileEntity.setId(id);
		return fileEntity;
	}

	public Long persistFileEntity(FileEntity fileEntity) throws Exception {
		Long id = fileDao.addFileRecord(fileEntity);
		return id;
	}

	public ProjectEntity createAndPersistProjectEntity() throws Exception {
		ProjectEntity projectEntity = createProjectEntity();
		Long id = persistProjectEntity(projectEntity);
		projectEntity.setId(id);
		return projectEntity;
	}

	public Long persistProjectEntity(ProjectEntity projectEntity) throws Exception {
		Long id = projectDao.addProjectRecord(projectEntity);
		return id;
	}

	public WorkspaceEntity createAndPersistWorkspaceEntity() throws Exception {
		WorkspaceEntity workspaceEntity = createWorkspaceEntity();
		Long id = persistWorkspaceEntity(workspaceEntity);
		workspaceEntity.setId(id);
		return workspaceEntity;
	}

	public Long persistWorkspaceEntity(WorkspaceEntity workspaceEntity) throws Exception {
		Long id = workspaceDao.addWorkspaceRecord(workspaceEntity);
		return id;
	}

}
