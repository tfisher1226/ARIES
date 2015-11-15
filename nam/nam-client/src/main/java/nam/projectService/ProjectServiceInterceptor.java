package nam.projectService;

import java.util.List;

import nam.model.Project;

import org.aries.message.Message;
import org.aries.message.MessageInterceptor;
import org.aries.util.ExceptionUtil;


@SuppressWarnings("serial")
public class ProjectServiceInterceptor extends MessageInterceptor<ProjectService> implements ProjectService {
	
	@Override
	public List<Project> getProjectList() {
		try {
			log.info("#### [nam]: getProjectList() sending...");
			Message request = createMessage("getProjectList");
			Message response = getProxy().invoke(request);
			List<Project> projectList = response.getPart("projectList");
			return projectList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Project getProjectById(Long id) {
		try {
			log.info("#### [nam]: getProjectById() sending...");
			Message request = createMessage("getProjectById");
			request.addPart("id", id);
			Message response = getProxy().invoke(request);
			Project project = response.getPart("project");
			return project;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Project getProjectByName(String name) {
		try {
			log.info("#### [nam]: getProjectByName() sending...");
			Message request = createMessage("getProjectByName");
			request.addPart("name", name);
			Message response = getProxy().invoke(request);
			Project project = response.getPart("project");
			return project;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Project> getProjectListByUserName(String userName) {
		try {
			log.info("#### [nam]: getProjectListByUserName() sending...");
			Message request = createMessage("getProjectListByUserName");
			request.addPart("userName", userName);
			Message response = getProxy().invoke(request);
			List<Project> projectList = response.getPart("projectList");
			return projectList;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public Long addProject(Project project) {
		try {
			log.info("#### [nam]: addProject() sending...");
			Message request = createMessage("addProject");
			request.addPart("project", project);
			Message response = getProxy().invoke(request);
			Long id = response.getPart("id");
			return id;
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveProject(Project project) {
		try {
			log.info("#### [nam]: saveProject() sending...");
			Message request = createMessage("saveProject");
			request.addPart("project", project);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void deleteProject(Project project) {
		try {
			log.info("#### [nam]: deleteProject() sending...");
			Message request = createMessage("deleteProject");
			request.addPart("project", project);
			getProxy().invoke(request);
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
