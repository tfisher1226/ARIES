package org.aries.ejb;

import javax.xml.bind.JAXBException;

import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jndi.JndiContext;
import org.aries.message.Message;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.registry.ServiceState;



public class EJBEndpointContext {

	private ServiceState serviceState;

	private OperationDescripter operationDescripter;

	private JndiContext jndiContext;

	private String jndiName;

	private EJBService service;

	private EJBClient client;

	private String serviceId;

	private Object mutex = new Object();


	public String getServiceId() {
		return this.serviceState.getServiceId();
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

	public JndiContext getJndiContext() {
		return jndiContext;
	}

	public void setJndiContext(JndiContext jndiContext) {
		this.jndiContext = jndiContext;
	}
	
	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	protected JAXBSessionCache getJAXBSessionCache() {
		return BeanContext.get(serviceState.getDomain() + ".jaxbSessionCache");
	}
	
	
	public void initialize() {
		//nothing for now
	}
	
//	public EJBService getService() {
//		if (service != null)
//			return service;
//		synchronized (mutex) {
//			try {
//				if (service == null) {
//					service = createService();
//					service.initialize();
//				}
//				return service;
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		}
//	}

//	public EJBClient getClient() {
//		if (client != null)
//			return client;
//		synchronized (mutex) {
//			try {
//				if (client == null) {
//					client = createClient();
//					client.initialize();
//				}
//				return client;
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		}
//	}

//	protected EJBService createService() {
//		Processor<String, String> processor = new EJBMessageDispatcher(this);
//		EJBService service = new EJBServiceImpl(this, processor);
//		return service;
//	}
//
//    protected EJBClient createClient() {
//    	EJBClient servicePort = new EJBClientImpl(this);
//		return servicePort;
//	}

    
	public String toXML(Message message) {
		try {
			JAXBWriter jaxb = getJAXBSessionCache().getWriter(serviceId);
			String xml = jaxb.marshal(message);
			return xml;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public Message fromXML(String xml) {
		try {
			JAXBReader jaxb = getJAXBSessionCache().getReader(serviceId);
			Message message = jaxb.unmarshal(xml);
			return message;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
}
