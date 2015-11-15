package org.aries.tx.service.rmi;

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractClient;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceException;
import org.aries.util.IdGenerator;
import org.aries.util.NameUtil;

import common.rmi.RMIClient;
import common.rmi.RMIClientImpl;
import common.rmi.RMIServiceRegistry;


public abstract class AbstractClientRMIImpl extends AbstractClient {

	private Log log = LogFactory.getLog(getClass());

	protected String serviceId;
	
	protected String host;

	protected int port;

	protected RMIClient client;


	public AbstractClientRMIImpl() {
		//nothing for now
	}
	
	public AbstractClientRMIImpl(String serviceId, String host, int port) {
		this.serviceId = serviceId;
		this.host = host;
		this.port = port;
	}
	
	public String getServiceUrl() {
        String url = "rmi://"+host+":"+port+"/"+serviceId;
		return url;
	}
	
	public void initialize() throws Exception {
		initializeClient();
	}
	
	protected void initializeClient() throws Exception {
		client = new RMIClientImpl(host, port, serviceId);
		client.initialize();
	}
	
	public void reset() throws Exception {
		//nothing for now
	}

	public void close() throws Exception {
		//nothing for now
	}
	

	@Override
	public <T extends Serializable> T invoke(Serializable request) throws Exception {
    	String correlationId = IdGenerator.createId();
    	return invoke(request, correlationId, 0L);
	}
	
	@Override
	public <T extends Serializable> T invoke(Serializable request, String correlationId, long timeout) throws Exception {
    	//request.addPart("ARIESServiceName", serviceId);
    	//request.addPart("ARIESMethodName", operationName);
    	
    	try {
    		//request.addPart(MessageConstants.PROPERTY_SERVICE_ID, serviceId);
    		//request.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
    		
    		String domain = NameUtil.getPackageName(serviceId);
    		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
			JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
			
    		String requestXml = jaxbWriter.marshal(request);
	    	String responseXml = client.invoke(requestXml, correlationId, 360000);
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
	
	@SuppressWarnings("unchecked")
	protected <T> T getServiceStub() {
		try {
			log.debug("********* looking up: "+getServiceUrl());
			Registry registry = RMIServiceRegistry.findRegistry(host, port);
			//Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
			T serviceStub = (T) registry.lookup(serviceId);
			return serviceStub;

		} catch (NotBoundException e) {
			log.error("********* service is not currently bound: "+e.getMessage());
			throw new RuntimeException(e);

		} catch (AccessException e) {
			log.error("********* operation is not permitted: "+e.getMessage());
			throw new RuntimeException(e);

		} catch (RemoteException e) {
			log.error("********* registry could not be contacted: "+e.getMessage());
			throw new RuntimeException(e);

		} catch (Throwable e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
}
