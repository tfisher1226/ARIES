package org.aries.rmi;

import java.io.Serializable;

import javax.xml.bind.JAXBException;

import org.aries.Assert;
import org.aries.Processor;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.message.Message;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.registry.ServiceState;

import common.rmi.RMIClient;
import common.rmi.RMIClientImpl;
import common.rmi.RMIService;
import common.rmi.RMIServiceImpl;


public class RMIEndpointContext {

	private RMIService service;

	private RMIClient client;

	private String host;

	private int port;

	private String serviceId;

	private ServiceState serviceState;

	private OperationDescripter operationDescripter;

	private Object mutex = new Object();


	public RMIEndpointContext(String host, int port, String serviceId) {
		Assert.notNull(host, "Host must be specified");
		Assert.isTrue(port > 0, "Port must be specified");
		Assert.notNull(serviceId, "Service ID must be specified");
		this.host = host;
		this.port = port;
		this.serviceId = serviceId;
	}

	public ServiceState getServiceState() {
		return this.serviceState;
	}

	public void setServiceState(ServiceState serviceState) {
		this.serviceState = serviceState;
	}
	
	public OperationDescripter getOperationDescripter() {
		return this.operationDescripter;
	}

	public void setOperationDescripter(OperationDescripter operationDescripter) {
		this.operationDescripter = operationDescripter;
	}
	
	protected JAXBSessionCache getJAXBSessionCache(String serviceId) {
		String key = serviceId + ".jaxbSessionCache";
		return BeanContext.get(key);
	}
	
	
	public void initialize() {
		//nothing for now
	}
	
	public RMIService getService() {
		if (service != null)
			return service;
		synchronized (mutex) {
			try {
				if (service == null) {
					service = createService(port, serviceId);
					service.initialize();
				}
				return service;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public RMIClient getClient() {
		if (client != null)
			return client;
		synchronized (mutex) {
			try {
				if (client == null) {
					client = createClient(host, port, serviceId);
					client.initialize();
				}
				return client;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected RMIService createService(int port, String serviceId) {
		Processor<Serializable, Serializable> processor = new RMIMessageDispatcher(serviceId);
		RMIServiceImpl service = new RMIServiceImpl(port, serviceId, processor);
		//TODO service.setSslEnabled(true);
		return service;
	}

    protected RMIClient createClient(String host, int port, String serviceId) {
    	RMIClientImpl client = new RMIClientImpl(host, port, serviceId);
		//TODO client.setSslEnabled(true);
		return client;
	}

	public String toXML(Message message) {
		try {
			JAXBSessionCache jaxbSessionCache = getJAXBSessionCache(serviceId);
			JAXBWriter jaxb = jaxbSessionCache.getWriter(serviceId);
			String xml = jaxb.marshal(message);
			return xml;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Message fromXML(String xml) {
		try {
			JAXBSessionCache jaxbSessionCache = getJAXBSessionCache(serviceId);
			JAXBReader jaxb = jaxbSessionCache.getReader(serviceId);
			Message message = jaxb.unmarshal(xml);
			return message;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void shutdown() throws Exception {
		if (client != null)
			client.close();
		if (service != null)
			service.close();
	}
    
}
