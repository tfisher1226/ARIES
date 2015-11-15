package admin.incoming.userService;

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

import admin.User;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class UserServiceInterceptor extends MessageInterceptor<UserService> {

	private static final Log log = LogFactory.getLog(UserServiceInterceptor.class);
	
	@Inject
	protected UserServiceHandler userServiceHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message getUserList(Message message) {
		try {
			List<User> userList = userServiceHandler.getUserList();
			Assert.notNull(userList, "UserList must exist");
			message.addPart("userList", userList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getUserById(Message message) {
		try {
			Long id = message.getPart("id");
			User user = userServiceHandler.getUserById(id);
			Assert.notNull(user, "User must exist");
			message.addPart("user", user);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message getUserByUserName(Message message) {
		try {
			String userName = message.getPart("userName");
			User user = userServiceHandler.getUserByUserName(userName);
			Assert.notNull(user, "User must exist");
			message.addPart("user", user);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message addUser(Message message) {
		try {
			User user = message.getPart("user");
			Long id = userServiceHandler.addUser(user);
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
	public Message saveUser(Message message) {
		try {
			User user = message.getPart("user");
			userServiceHandler.saveUser(user);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message deleteUser(Message message) {
		try {
			User user = message.getPart("user");
			userServiceHandler.deleteUser(user);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
