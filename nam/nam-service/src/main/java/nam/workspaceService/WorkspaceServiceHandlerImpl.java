package nam.workspaceService;

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

import nam.NamRepository;
import nam.model.Workspace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;


@Stateful
@Local(WorkspaceServiceHandler.class)
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Interceptors(ServiceHandlerInterceptor.class)
public class WorkspaceServiceHandlerImpl extends AbstractServiceHandler implements WorkspaceServiceHandler {
	
	private static final Log log = LogFactory.getLog(WorkspaceServiceHandlerImpl.class);
	
	@Inject
	protected NamRepository namRepository;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	public NamRepository getNamRepository() {
		return namRepository;
	}
	
	public void setNamRepository(NamRepository namRepository) {
		this.namRepository = namRepository;
	}
	
	public String getName() {
		return "WorkspaceService";
	}
	
	public String getDomain() {
		return "aries.org";
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
	public List<Workspace> getWorkspaceList() {
		log.info("#### [nam]: WorkspaceService request received");
		
		try {
			List<Workspace> workspaceList = namRepository.getAllWorkspaceRecords();
			return workspaceList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}

	@TransactionAttribute(REQUIRED)
	public Workspace getWorkspaceById(Long id) {
		log.info("#### [nam]: WorkspaceService request received");
		
		try {
			Workspace workspace = namRepository.getWorkspaceRecordById(id);
			return workspace;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Workspace getWorkspaceByName(String name) {
		log.info("#### [nam]: WorkspaceService request received");
		
		try {
			Workspace workspace = namRepository.getWorkspaceRecordByName(name);
			return workspace;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}

	@TransactionAttribute(REQUIRED)
	public Long addWorkspace(Workspace workspace) {
		log.info("#### [nam]: WorkspaceService request received");
		
		try {
			Long id = namRepository.addWorkspaceRecord(workspace);
			return id;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void saveWorkspace(Workspace workspace) {
		log.info("#### [nam]: WorkspaceService request received");
		
		try {
			namRepository.saveWorkspaceRecord(workspace);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void deleteWorkspace(Workspace workspace) {
		log.info("#### [nam]: WorkspaceService request received");
		
		try {
			namRepository.removeWorkspaceRecord(workspace);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
}
