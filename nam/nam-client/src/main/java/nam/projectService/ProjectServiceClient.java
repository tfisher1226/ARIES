package nam.projectService;

import java.util.List;

import nam.model.Project;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractDelegate;
import org.aries.util.ExceptionUtil;


public class ProjectServiceClient extends AbstractDelegate implements ProjectService {
	
	private static final Log log = LogFactory.getLog(ProjectServiceClient.class);
	

	@Override
	public String getDomain() {
		return "aries.org";
	}
	
	@Override
	public String getServiceId() {
		return ProjectService.ID;
	}
	
	@SuppressWarnings("unchecked")
	public ProjectService getProxy() throws Exception {
		return getProxy(ProjectService.ID);
	}

	@Override
	public List<Project> getProjectList() {
		try {
			List<Project> projectList = getProxy().getProjectList();
			return projectList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Project getProjectById(Long id) {
		try {
			Project project = getProxy().getProjectById(id);
			return project;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Project getProjectByName(String name) {
		try {
			Project project = getProxy().getProjectByName(name);
			return project;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Project> getProjectListByUserName(String userName) {
		try {
			List<Project> projectList = getProxy().getProjectListByUserName(userName);
			return projectList;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addProject(Project project) {
		try {
		Long id = getProxy().addProject(project);
			return id;
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveProject(Project project) {
		try {
			getProxy().saveProject(project);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteProject(Project project) {
		try {
			getProxy().deleteProject(project);
		} catch (Exception e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
