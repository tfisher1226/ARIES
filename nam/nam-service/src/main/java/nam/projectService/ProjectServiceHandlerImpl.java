package nam.projectService;

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
import nam.model.Project;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.service.AbstractServiceHandler;
import org.aries.tx.service.ServiceHandlerInterceptor;
import org.aries.util.ExceptionUtil;


@Stateful
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
@Local(ProjectServiceHandler.class)
@Interceptors(ServiceHandlerInterceptor.class)
public class ProjectServiceHandlerImpl extends AbstractServiceHandler implements ProjectServiceHandler {
	
	private static final Log log = LogFactory.getLog(ProjectServiceHandlerImpl.class);
	
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
		return "ProjectService";
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
	public List<Project> getProjectList() {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			List<Project> projectList = namRepository.getAllProjectRecords();
			return projectList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Project getProjectById(Long id) {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			Project project = namRepository.getProjectRecordById(id);
			return project;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Project getProjectByName(String name) {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			Project project = namRepository.getProjectRecordByName(name);
			return project;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public List<Project> getProjectListByUser(String user) {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			List<Project> projectList = namRepository.getProjectRecordsByUser(user);
			return projectList;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public Long addProject(Project project) {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			Long id = namRepository.addProjectRecord(project);
			return id;
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void saveProject(Project project) {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			namRepository.saveProjectRecord(project);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
	@TransactionAttribute(REQUIRED)
	public void deleteProject(Project project) {
		log.info("#### [nam]: ProjectService request received");
		
		try {
			namRepository.removeProjectRecord(project);
		
		} catch (Throwable e) {
			log.error("Error", e);
			transactionSynchronizationRegistry.setRollbackOnly();
			RuntimeException rootCause = ExceptionUtil.rewrap(e);
			throw rootCause;
		}
	}
	
}
