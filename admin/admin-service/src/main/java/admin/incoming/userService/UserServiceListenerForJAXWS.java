package admin.incoming.userService;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;

import admin.User;


@Remote(UserService.class)
@Stateless(name = "UserService")
@WebService(name = "userService", serviceName = "userServiceService", portName = "userService", targetNamespace = "http://admin")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class UserServiceListenerForJAXWS implements UserService {
	
	private static final Log log = LogFactory.getLog(UserServiceListenerForJAXWS.class);
	
	@Inject
	private UserServiceHandler userServiceHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod
	@WebResult(name = "userList")
	@TransactionAttribute(REQUIRED)
	public List<User> getUserList() {
		if (!Bootstrapper.isInitialized("admin-service"))
			return null;
		
		try {
			List<User> userList = userServiceHandler.getUserList();
			Assert.notNull(userList, "UserList must exist");
			return userList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "user")
	@TransactionAttribute(REQUIRED)
	public User getUserById(Long id) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			User user = userServiceHandler.getUserById(id);
			Assert.notNull(user, "User must exist");
			return user;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "user")
	@TransactionAttribute(REQUIRED)
	public User getUserByUserName(String userName) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			User user = userServiceHandler.getUserByUserName(userName);
			Assert.notNull(user, "User must exist");
			return user;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "id")
	@TransactionAttribute(REQUIRED)
	public Long addUser(User user) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Long id = userServiceHandler.addUser(user);
			Assert.notNull(id, "Id must exist");
			return id;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void saveUser(User user) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			userServiceHandler.saveUser(user);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void deleteUser(User user) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			userServiceHandler.deleteUser(user);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
