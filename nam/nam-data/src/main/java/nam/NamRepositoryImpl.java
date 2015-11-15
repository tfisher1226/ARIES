package nam;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.inject.Inject;

import nam.bean.ProjectManager;
import nam.bean.WorkspaceManager;
import nam.model.Project;
import nam.model.Workspace;

import org.aries.Assert;
import org.aries.data.AbstractRepository;
import org.aries.util.ExceptionUtil;


@Stateful
@Local(NamRepository.class)
public class NamRepositoryImpl extends AbstractRepository implements NamRepository {
	
	@Inject
	protected WorkspaceManager workspaceManager;
	
	@Inject
	protected ProjectManager projectManager;
	
	
	public WorkspaceManager getWorkspaceManager() {
		return workspaceManager;
	}
	
	public void setWorkspaceManager(WorkspaceManager workspaceManager) {
		this.workspaceManager = workspaceManager;
	}
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}
	
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	@Override
	public void clearContext() {
		try {
			workspaceManager.clearContext();
			projectManager.clearContext();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Workspace> getAllWorkspaceRecords() {
		try {
			List<Workspace> records = workspaceManager.getAllWorkspaceRecords();
			Assert.notNull(records, "Workspace record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Workspace getWorkspaceRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Workspace record = workspaceManager.getWorkspaceRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Workspace getWorkspaceRecordByName(String name) {
		try {
			Assert.notNull(name, "Name must be specified");
			Workspace record = workspaceManager.getWorkspaceRecordByName(name);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
//	@Override
//	public List<Workspace> getWorkspaceRecordsByName(PersonName name) {
//		try {
//			Assert.notNull(name, "Name must be specified");
//			List<Workspace> records = workspaceManager.getWorkspaceRecordsByName(name);
//			Assert.notNull(records, "Workspace record list null");
//			return records;
//			
//		} catch (Exception e) {
//			throw ExceptionUtil.createRuntimeException(e);
//		}
//	}
	
	@Override
	public List<Workspace> getWorkspaceRecordsByUser(String user) {
		try {
			Assert.notNull(user, "User must be specified");
			List<Workspace> records = workspaceManager.getWorkspaceRecordsByUser(user);
			Assert.notNull(records, "Workspace record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Workspace> getWorkspaceRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Workspace> records = workspaceManager.getWorkspaceRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Workspace record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addWorkspaceRecord(Workspace workspace) {
		try {
			Assert.notNull(workspace, "Workspace record must be specified");
			Long id = workspaceManager.addWorkspaceRecord(workspace);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveWorkspaceRecord(Workspace workspace) {
		try {
			Assert.notNull(workspace, "Workspace record must be specified");
			workspaceManager.saveWorkspaceRecord(workspace);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllWorkspaceRecords() {
		try {
			workspaceManager.removeAllWorkspaceRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeWorkspaceRecord(Workspace workspace) {
		try {
			Assert.notNull(workspace, "Workspace record must be specified");
			workspaceManager.removeWorkspaceRecord(workspace);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeWorkspaceRecord(Long id) {
		try {
			Assert.notNull(id, "Workspace record ID must be specified");
			workspaceManager.removeWorkspaceRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importWorkspaceRecords() {
		try {
			workspaceManager.importWorkspaceRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Project> getAllProjectRecords() {
		try {
			List<Project> records = projectManager.getAllProjectRecords();
			Assert.notNull(records, "Project record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Project getProjectRecordById(Long id) {
		try {
			Assert.notNull(id, "Id must be specified");
			Project record = projectManager.getProjectRecordById(id);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Project getProjectRecordByName(String name) {
		try {
			Assert.notNull(name, "Name must be specified");
			Project record = projectManager.getProjectRecordByName(name);
			return record;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Project> getProjectRecordsByUser(String owner) {
		try {
			Assert.notNull(owner, "Owner must be specified");
			List<Project> records = projectManager.getProjectRecordsByOwner(owner);
			Assert.notNull(records, "Project record list null");
			return records;
			
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public List<Project> getProjectRecordsByPage(int pageIndex, int pageSize) {
		try {
			Assert.notNull(pageIndex, "Page-index must be specified");
			Assert.notNull(pageSize, "Page-size must be specified");
			List<Project> records = projectManager.getProjectRecordsByPage(pageIndex, pageSize);
			Assert.notNull(records, "Project record list null");
			return records;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public Long addProjectRecord(Project project) {
		try {
			Assert.notNull(project, "Project record must be specified");
			Long id = projectManager.addProjectRecord(project);
			Assert.notNull(id, "Id must exist");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void saveProjectRecord(Project project) {
		try {
			Assert.notNull(project, "Project record must be specified");
			projectManager.saveProjectRecord(project);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeAllProjectRecords() {
		try {
			projectManager.removeAllProjectRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeProjectRecord(Project project) {
		try {
			Assert.notNull(project, "Project record must be specified");
			projectManager.removeProjectRecord(project);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void removeProjectRecord(Long id) {
		try {
			Assert.notNull(id, "Project record ID must be specified");
			projectManager.removeProjectRecord(id);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
	@Override
	public void importProjectRecords() {
		try {
			projectManager.importProjectRecords();
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
	
}
