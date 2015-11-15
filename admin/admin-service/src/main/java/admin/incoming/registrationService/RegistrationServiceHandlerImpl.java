package admin.incoming.registrationService;

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
import admin.Registration;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Local(RegistrationServiceHandler.class)
@Interceptors(ServiceHandlerInterceptor.class)
public class RegistrationServiceHandlerImpl extends AbstractServiceHandler implements RegistrationServiceHandler {
	
	private static final Log log = LogFactory.getLog(RegistrationServiceHandlerImpl.class);
	
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
		return "RegistrationService";
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
	public List<Registration> getRegistrationList() {
		log.info("#### [admin]: RegistrationService request received");
		
		try {
			List<Registration> registrationList = adminRepository.getAllRegistrationRecords();
			return registrationList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Registration getRegistrationById(Long id) {
		log.info("#### [admin]: RegistrationService request received");
		
		try {
			Registration registration = adminRepository.getRegistrationRecordById(id);
			return registration;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Long addRegistration(Registration registration) {
		log.info("#### [admin]: RegistrationService request received");
		
		try {
			Long id = adminRepository.addRegistrationRecord(registration);
			return id;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void saveRegistration(Registration registration) {
		log.info("#### [admin]: RegistrationService request received");
		
		try {
			adminRepository.saveRegistrationRecord(registration);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void deleteRegistration(Registration registration) {
		log.info("#### [admin]: RegistrationService request received");
		
		try {
			adminRepository.removeRegistrationRecord(registration);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
}
