package org.aries.ejb;

import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Handler;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.service.AbstractServiceProxy;
import org.aries.service.ServiceException;
import org.aries.service.ServiceProxy;


public class EJBServiceProxy extends AbstractServiceProxy implements ServiceProxy {

	private static Log log = LogFactory.getLog(ServiceProxy.class);

	private EJBEndpointContext endpointContext;

	private EJBClient client;
	
	
	public EJBServiceProxy(EJBEndpointContext endpointContext) {
		this.endpointContext = endpointContext;
	}

	public void initialize() throws Exception {
    	endpointContext.initialize();
    	initializeClient();
	}

    protected void initializeClient() {
		//client = lookupClientStub(endpointContext);
		client = createClient();
		client.initialize();
	}

    protected EJBClient createClient() {
    	EJBClientImpl client = new EJBClientImpl();
		client.setEndpointContext(endpointContext);
		return client;
	}

	public String getUrl() {
		return endpointContext.getJndiContext().getConnectionUrl();
	}
	
	public String getServiceId() {
		return endpointContext.getServiceId();
	}

	public String getOperationName() {
		return endpointContext.getOperationDescripter().getName();
	}

	//TODO separate below here to another class

    //TODO add timeout to interface
	public Message invoke(Message request) {
		String serviceId = getServiceId();
		String operationName = getOperationName();
		String correlationId = (String) request.getCorrelationId();
    	//request.addPart("ARIESServiceName", serviceId);
    	//request.addPart("ARIESMethodName", operationName);
    	
    	try {
    		JAXBSessionCache jaxbSessionCache = endpointContext.getJAXBSessionCache();
			JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
			
    		String requestXml = jaxbWriter.marshal(request);
	    	String responseXml = client.invoke(requestXml, correlationId, 360000);
    		Message response = jaxbReader.unmarshal(responseXml);
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

//	public Message invoke(Message request) {
//    	request.addPart("serviceName", "org.aries:"+_serviceName);
//    	try {
//	    	String requestXML = Transformer.toXML(request);
//	    	String responseXML = _servicePort.invoke(requestXML);
//	    	Message response = Transformer.toObject(responseXML);
//			return response;
//		} catch (Throwable e) {
//			log.error(e);
//			//assuming e is acceptable for propagation (i.e. not like an SQLException etc...)
//			throw new ServiceException(e);
//		}
//	}

	public Future<Message> invoke(Message request, Handler<Message> handler) {
		return null;
	}

	@Override
	public void dispatch(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message receive(long messageTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	

//	protected EJBClient lookupClientStub(EJBEndpointContext endpointContext) {
//		EJBClientRemote<Message> client = lookupStub();
//    	client.setEndpointContext(endpointContext);
//		return client;
//	}
//
//
//	@SuppressWarnings("unchecked")
//	protected EJBClientRemote<Message> lookupStub() {
//		JndiContext jndiContext = endpointContext.getJndiContext();
//		//String methodName = endpointContext.getOperationDescripter().getName();
//		//ResultDescripter resultDescripter = endpointContext.getOperationDescripter().getResultDescripter();
//		//List<ParameterDescripter> parameterDescripters = endpointContext.getOperationDescripter().getParameterDescripters();
//		String jndiName = endpointContext.getJndiName();
//		
//		try {
//			log.debug("********* looking up: "+jndiName);
//			Object object = jndiContext.lookupObject("deploy/common.rmi.EJBClient/remote");
//			
//			if (object instanceof EJBServiceRemote == false) {
//				//TODO Throw a meaningful exception from here
//				throw new RuntimeException("Unexpected stub: "+object.getClass());
//			}
//				
//			EJBClientRemote<Message> stub = (EJBClientRemote<Message>) object; 
//			return stub;
//			
////		} catch (NotBoundException e) {
////			log.error("********* service is not currently bound: "+e.getMessage());
////			throw new RuntimeException(e);
////
////		} catch (AccessException e) {
////			log.error("********* operation is not permitted: "+e.getMessage());
////			throw new RuntimeException(e);
////
////		} catch (RemoteException e) {
////			log.error("********* registry could not be contacted: "+e.getMessage());
////			throw new RuntimeException(e);
//
//		} catch (NamingException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
//			log.error("Error: "+rootCauseMessage, rootCause);
//			//TODO Throw a meaningful exception from here
//			throw new RuntimeException(e);
//
//		} catch (Throwable e) {
//			log.error(e);
//			throw new RuntimeException(e);
//		}
//	}
	
}
