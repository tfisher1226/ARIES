package nam.workspaceService;

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

import nam.model.Workspace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class WorkspaceServiceInterceptor extends MessageInterceptor<WorkspaceService> {

	private static final Log log = LogFactory.getLog(WorkspaceServiceInterceptor.class);
	
	@Inject
	protected WorkspaceServiceHandler workspaceServiceHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message getWorkspaceList(Message message) {
		try {
			List<Workspace> workspaceList = workspaceServiceHandler.getWorkspaceList();
			Assert.notNull(workspaceList, "WorkspaceList must exist");
			message.addPart("workspaceList", workspaceList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getWorkspaceById(Message message) {
		try {
			Long id = message.getPart("id");
			Workspace workspace = workspaceServiceHandler.getWorkspaceById(id);
			Assert.notNull(workspace, "Workspace must exist");
			message.addPart("workspace", workspace);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getWorkspaceByName(Message message) {
		try {
			String name = message.getPart("name");
			Workspace workspace = workspaceServiceHandler.getWorkspaceByName(name);
			Assert.notNull(workspace, "Workspace must exist");
			message.addPart("workspace", workspace);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message addWorkspace(Message message) {
		try {
			Workspace workspace = message.getPart("workspace");
			Long id = workspaceServiceHandler.addWorkspace(workspace);
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
	public Message saveWorkspace(Message message) {
		try {
			Workspace workspace = message.getPart("workspace");
			workspaceServiceHandler.saveWorkspace(workspace);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message deleteWorkspace(Message message) {
		try {
			Workspace workspace = message.getPart("workspace");
			workspaceServiceHandler.deleteWorkspace(workspace);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
