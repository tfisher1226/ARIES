package admin.incoming.userService;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.process.TimeoutHandler;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import admin.AdminRepository;
import admin.User;


@Stateful
@Local(UserServiceHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class UserServiceHandlerImpl extends AbstractServiceHandler implements UserServiceHandler {
	
	private static final Log log = LogFactory.getLog(UserServiceHandlerImpl.class);
	
	@Inject
	protected AdminRepository adminRepository;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public AdminRepository getAdminRepository() {
		return adminRepository;
	}
	
	public void setAdminRepository(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	public String getName() {
		return "UserService";
	}
	
	public String getDomain() {
		return "admin";
	}
	
	public boolean prepare(String transactionId) {
		return true;
	}
	
	public void commit(String transactionId) {
		//nothing for now
	}
	
	public void rollback(String transactionId) {
		//nothing for now
	}
	
	@TransactionAttribute(REQUIRED)
	public List<User> getUserList() {
		log.info("#### [admin]: UserService request received");
		
		try {
			List<User> userList = adminRepository.getAllUserRecords();
			return userList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}

	@TransactionAttribute(REQUIRED)
	public User getUserById(Long id) {
		log.info("#### [admin]: UserService request received");
		
		try {
			User user = adminRepository.getUserRecordById(id);
			return user;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public User getUserByUserName(String userName) {
		log.info("#### [admin]: UserService request received");
		
		try {
			User user = adminRepository.getUserRecordByUserName(userName);
			return user;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}

	@TransactionAttribute(REQUIRED)
	public Long addUser(User user) {
		log.info("#### [admin]: UserService request received");
		
		try {
			Long id = adminRepository.addUserRecord(user);
			return id;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void saveUser(User user) {
		log.info("#### [admin]: UserService request received");
		
		try {
			adminRepository.saveUserRecord(user);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void deleteUser(User user) {
		log.info("#### [admin]: UserService request received");
		
		try {
			adminRepository.removeUserRecord(user);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
}
