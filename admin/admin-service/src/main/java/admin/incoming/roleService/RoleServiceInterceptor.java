package admin.incoming.roleService;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import admin.Role;
import admin.RoleType;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class RoleServiceInterceptor extends MessageInterceptor<RoleService> {
	
	private static final Log log = LogFactory.getLog(RoleServiceInterceptor.class);
	
	@Inject
	protected RoleServiceHandler roleServiceHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message getRoleList(Message message) {
		try {
			List<Role> roleList = roleServiceHandler.getRoleList();
			Assert.notNull(roleList, "RoleList must exist");
			message.addPart("roleList", roleList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getRoleById(Message message) {
		try {
			Long id = message.getPart("id");
			Role role = roleServiceHandler.getRoleById(id);
			Assert.notNull(role, "Role must exist");
			message.addPart("role", role);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getRoleByName(Message message) {
		try {
			String name = message.getPart("name");
			Role role = roleServiceHandler.getRoleByName(name);
			Assert.notNull(role, "Role must exist");
			message.addPart("role", role);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getRoleListByRoleType(Message message) {
		try {
			RoleType roleType = message.getPart("roleType");
			List<Role> roleList = roleServiceHandler.getRoleListByRoleType(roleType);
			Assert.notNull(roleList, "RoleList must exist");
			message.addPart("roleList", roleList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}
		
		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message addRole(Message message) {
		try {
			Role role = message.getPart("role");
			Long id = roleServiceHandler.addRole(role);
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
	public Message saveRole(Message message) {
		try {
			Role role = message.getPart("role");
			roleServiceHandler.saveRole(role);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message deleteRole(Message message) {
		try {
			Role role = message.getPart("role");
			roleServiceHandler.deleteRole(role);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
