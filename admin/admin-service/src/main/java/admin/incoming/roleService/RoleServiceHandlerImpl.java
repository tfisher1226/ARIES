package admin.incoming.roleService;

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
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;

import admin.AdminRepository;
import admin.Role;
import admin.RoleType;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Local(RoleServiceHandler.class)
@Interceptors(ServiceHandlerInterceptor.class)
public class RoleServiceHandlerImpl extends AbstractServiceHandler implements RoleServiceHandler {
	
	private static final Log log = LogFactory.getLog(RoleServiceHandlerImpl.class);
	
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
		return "RoleService";
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
	public List<Role> getRoleList() {
		log.info("#### [admin]: RoleService request received");
		
		try {
			List<Role> roleList = adminRepository.getAllRoleRecords();
			return roleList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Role getRoleById(Long id) {
		log.info("#### [admin]: RoleService request received");
		
		try {
			Role role = adminRepository.getRoleRecordById(id);
			return role;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Role getRoleByName(String name) {
		log.info("#### [admin]: RoleService request received");
		
		try {
			Role role = adminRepository.getRoleRecordByName(name);
			return role;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Role> getRoleListByRoleType(RoleType roleType) {
		log.info("#### [admin]: RoleService request received");
		
		try {
			List<Role> roleList = adminRepository.getRoleRecordsByRoleType(roleType);
			return roleList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Long addRole(Role role) {
		log.info("#### [admin]: RoleService request received");
		
		try {
			Long id = adminRepository.addRoleRecord(role);
			return id;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void saveRole(Role role) {
		log.info("#### [admin]: RoleService request received");
		
		try {
			adminRepository.saveRoleRecord(role);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void deleteRole(Role role) {
		log.info("#### [admin]: RoleService request received");
		
		try {
			adminRepository.removeRoleRecord(role);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
}
