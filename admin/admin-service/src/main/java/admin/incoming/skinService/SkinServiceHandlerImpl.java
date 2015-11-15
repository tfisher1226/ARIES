package admin.incoming.skinService;

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
import admin.Skin;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Local(SkinServiceHandler.class)
@Interceptors(ServiceHandlerInterceptor.class)
public class SkinServiceHandlerImpl extends AbstractServiceHandler implements SkinServiceHandler {
	
	private static final Log log = LogFactory.getLog(SkinServiceHandlerImpl.class);
	
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
		return "SkinService";
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
	public List<Skin> getSkinList() {
		log.info("#### [admin]: SkinService request received");
		
		try {
			List<Skin> skinList = adminRepository.getAllSkinRecords();
			return skinList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Skin getSkinById(Long id) {
		log.info("#### [admin]: SkinService request received");
		
		try {
			Skin skin = adminRepository.getSkinRecordById(id);
			return skin;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Skin getSkinByName(String name) {
		log.info("#### [admin]: SkinService request received");
		
		try {
			Skin skin = adminRepository.getSkinRecordByName(name);
			return skin;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Long addSkin(Skin skin) {
		log.info("#### [admin]: SkinService request received");
		
		try {
			Long id = adminRepository.addSkinRecord(skin);
			return id;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void saveSkin(Skin skin) {
		log.info("#### [admin]: SkinService request received");
		
		try {
			adminRepository.saveSkinRecord(skin);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void deleteSkin(Skin skin) {
		log.info("#### [admin]: SkinService request received");
		
		try {
			adminRepository.removeSkinRecord(skin);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
}
