package nam.projectService;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import nam.model.Project;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class ProjectServiceInterceptor extends MessageInterceptor<ProjectService> {
	
	private static final Log log = LogFactory.getLog(ProjectServiceInterceptor.class);
	
	@Inject
	protected ProjectServiceHandler projectServiceHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message getProjectList(Message message) {
		try {
			List<Project> projectList = projectServiceHandler.getProjectList();
			Assert.notNull(projectList, "ProjectList must exist");
			message.addPart("projectList", projectList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getProjectById(Message message) {
		try {
			Long id = message.getPart("id");
			Project project = projectServiceHandler.getProjectById(id);
			Assert.notNull(project, "Project must exist");
			message.addPart("project", project);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getProjectByName(Message message) {
		try {
			String name = message.getPart("name");
			Project project = projectServiceHandler.getProjectByName(name);
			Assert.notNull(project, "Project must exist");
			message.addPart("project", project);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getProjectListByUser(Message message) {
		try {
			String user = message.getPart("user");
			List<Project> projectList = projectServiceHandler.getProjectListByUser(user);
			Assert.notNull(projectList, "ProjectList must exist");
			message.addPart("projectList", projectList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addProject(Message message) {
		try {
			Project project = message.getPart("project");
			Long id = projectServiceHandler.addProject(project);
			Assert.notNull(id, "Id must exist");
			message.addPart("id", id);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message saveProject(Message message) {
		try {
			Project project = message.getPart("project");
			projectServiceHandler.saveProject(project);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message deleteProject(Message message) {
		try {
			Project project = message.getPart("project");
			projectServiceHandler.deleteProject(project);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
