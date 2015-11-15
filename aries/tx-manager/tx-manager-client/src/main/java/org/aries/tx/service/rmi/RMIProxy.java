package org.aries.tx.service.rmi;

import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Handler;
import org.aries.client.AbstractEndpoint;
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


public class RMIProxy extends AbstractEndpoint {

	private Log log = LogFactory.getLog(getClass());

	protected String serviceId;
	
	protected String host;

	protected int port;

	protected RMIClient client;

	private AtomicBoolean initialized = new AtomicBoolean();
	

	public RMIProxy() {
		//nothing for now
	}
	
	public RMIProxy(String serviceId, String host, int port) {
		this.serviceId = serviceId;
		this.host = host;
		this.port = port;
	}
	
	//@Override
	public String getServiceId() {
		return serviceId;
	}
	
	//@Override
	public String getServiceUrl() {
        String url = "rmi://"+host+":"+port+"/"+serviceId;
		return url;
	}
	
	@Override
	public void initialize() throws Exception {
		initializeClient();
	}
	
	protected void initializeClient() throws Exception {
		client = new RMIClientImpl(host, port, serviceId);
		client.initialize();
	}

	@Override
	public void reset() throws Exception {
		//nothing for now
	}

	@Override
	public void close() throws Exception {
		//nothing for now
	}
	
	//@Override
	public void send(Serializable request) throws Exception {
		// TODO Auto-generated method stub
	}
	
	//@Override
	public <T extends Serializable> T invoke(Serializable request) throws Exception {
		if (!initialized.get())
			initialize();
		
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
    		RMIListener serviceStub = getServiceStub();
	    	String responseXml = serviceStub.invoke(requestXml, correlationId, 360000);
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
	
	
	private Map<String, Handler<?>> handlers;
	
	public <T extends Serializable> void addResponseHandler(String messageClass, Handler<T> handler) {
	}
	
}
