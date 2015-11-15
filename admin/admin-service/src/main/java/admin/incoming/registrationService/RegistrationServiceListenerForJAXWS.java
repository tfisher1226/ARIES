package admin.incoming.registrationService;

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

import admin.Registration;


@Remote(RegistrationService.class)
@Stateless(name = "RegistrationService")
@WebService(name = "registrationService", serviceName = "registrationServiceService", portName = "registrationService", targetNamespace = "http://admin")
@HandlerChain(file = "/jaxws-handlers-service-oneway.xml")
public class RegistrationServiceListenerForJAXWS implements RegistrationService {
	
	private static final Log log = LogFactory.getLog(RegistrationServiceListenerForJAXWS.class);
	
	@Inject
	private RegistrationServiceHandler registrationServiceHandler;
	
	@Resource
	private WebServiceContext webServiceContext;
	
	@Resource
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	
	
	@Override
	@WebMethod
	@WebResult(name = "registrationList")
	@TransactionAttribute(REQUIRED)
	public List<Registration> getRegistrationList() {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			List<Registration> registrationList = registrationServiceHandler.getRegistrationList();
			Assert.notNull(registrationList, "RegistrationList must exist");
			return registrationList;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "registration")
	@TransactionAttribute(REQUIRED)
	public Registration getRegistrationById(Long id) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Registration registration = registrationServiceHandler.getRegistrationById(id);
			Assert.notNull(registration, "Registration must exist");
			return registration;
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@WebResult(name = "id")
	@TransactionAttribute(REQUIRED)
	public Long addRegistration(Registration registration) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return null;
		
		try {
			Long id = registrationServiceHandler.addRegistration(registration);
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
	public void saveRegistration(Registration registration) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			registrationServiceHandler.saveRegistration(registration);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	@WebMethod
	@TransactionAttribute(REQUIRED)
	public void deleteRegistration(Registration registration) {
		if (!Bootstrapper.isInitialized("admin.service"))
			return;
		
		try {
			registrationServiceHandler.deleteRegistration(registration);
			
		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
