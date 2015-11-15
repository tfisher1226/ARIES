package org.aries.tx.service.ejb;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Handler;
import org.aries.client.AbstractEndpoint;
import org.aries.ejb.EJBClient;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jndi.JndiName;
import org.aries.runtime.BeanContext;
import org.aries.runtime.BeanLocator;
import org.aries.service.ServiceException;
import org.aries.util.IdGenerator;
import org.aries.util.NameUtil;


public class EJBProxy extends AbstractEndpoint {

	private Log log = LogFactory.getLog(getClass());
	
	protected String applicationName;

	protected String moduleName;

	protected String beanName;

	protected String hostName;

	protected int portNumber;

	protected String serviceId;
	
	protected EJBClient client;
	

	public EJBProxy() {
		//nothing for now
	}
	
	public EJBProxy(String serviceId, String host, int port) {
		this.serviceId = serviceId;
	}
	
	//@Override
	public String getServiceId() {
		return serviceId;
	}
	
	//@Override
	public String getServiceUrl() {
		//nothing for now
		return null;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public void initialize() throws Exception {
		initializeClient();
	}
	
	protected void initializeClient() throws Exception {
//		client = new EJBClientImpl(host, port, serviceId);
//		client.initialize();
	}
	
	@Override
	public void reset() throws Exception {
		//nothing for now
	}

	@Override
	public void close() throws Exception {
		//nothing for now
	}

	
	protected EJBListener getServiceListener() {
		String jndiName = getServiceListenerJndiName();
		return BeanLocator.lookup(EJBListener.class, jndiName);
	}
	
	protected String getServiceListenerJndiName() {
		JndiName jndiName = new JndiName();
		jndiName.setApplicationName(applicationName);
		//jndiName.setApplicationName("bookshop2-app");
		jndiName.setModuleName(moduleName);
		//jndiName.setModuleName("admin-service-0.0.1-SNAPSHOT");
		jndiName.setBeanName(beanName);
		//jndiName.setBeanName("UserServiceListenerEJBImpl");
		return jndiName.toString();
	}
	
	//@Override
	public void send(Serializable request) throws Exception {
		// TODO Auto-generated method stub
	}
	
	//@Override
	public <T extends Serializable> T invoke(Serializable request) {
    	//request.addPart("ARIESServiceName", serviceId);
    	//request.addPart("ARIESMethodName", operationName);
    	String correlationId = IdGenerator.createId();
    	
    	try {
    		//request.addPart(MessageConstants.PROPERTY_SERVICE_ID, serviceId);
    		//request.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
    		
    		String domain = NameUtil.getPackageName(serviceId);
    		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
			JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
			
    		String requestXml = jaxbWriter.marshal(request);
	    	String responseXml = getServiceListener().invoke(requestXml, correlationId, 360000L);
    		T response = jaxbReader.unmarshal(responseXml);
			return response;
			
		} catch (RuntimeException e) {
			log.error("Error", e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			throw e;
			
		} catch (Throwable e) {
			log.error(e);
			//assuming for now e is acceptable for propagation 
			//(i.e. not like an SQLException etc...)
			throw new ServiceException(e);
		}
	}
	
//	protected Message invokeXXX(Message request) {
//		try {
//			EJBListener client = getServiceStub();
//			Message response = client.receive(request);
//			return response;
//		} catch (Exception e) {
//			Throwable cause = e.getCause();
//			if (cause == null)
//				cause = e;
//			throw new RuntimeException(cause.getLocalizedMessage());
//		}
//	}
	
	private Map<String, Handler<?>> handlers;
	
	public <T extends Serializable> void addResponseHandler(String messageClass, Handler<T> handler) {
	}
	
}
