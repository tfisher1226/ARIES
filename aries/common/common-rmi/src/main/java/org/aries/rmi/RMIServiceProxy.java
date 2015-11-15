package org.aries.rmi;

import java.util.concurrent.Future;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.Handler;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;
import org.aries.service.AbstractServiceProxy;
import org.aries.service.ServiceException;
import org.aries.service.ServiceProxy;
import org.aries.util.IdGenerator;
import org.aries.util.NameUtil;

import common.rmi.RMIClient;
import common.rmi.RMIClientImpl;


public class RMIServiceProxy extends AbstractServiceProxy implements ServiceProxy {

	private static Log log = LogFactory.getLog(ServiceProxy.class);

	private String host = "localhost";

	private int port;

	//private String context;

	private String serviceId;

	private String operationName;

	private RMIClient client;
	
	
//    public RMIServiceProxy(String context, String service) {
//    	Assert.notNull(context, "Context must be specified");
//    	Assert.notNull(service, "Service must be specified");
//    	this.service = service;
//    	initialize(context);
//    }

    public RMIServiceProxy(String host, int port, String context, String serviceId, String operationName) {
    	Assert.notNull(host, "Host must be specified");
    	Assert.isTrue(port > 0, "Port must be specified");
    	//Assert.notNull(context, "Context must be specified");
    	Assert.notNull(serviceId, "ServiceId must be specified");
    	Assert.notNull(operationName, "OperationName must be specified");
    	this.host = host;
    	this.port = port;
    	//this.context = context;
    	this.serviceId = serviceId;
    	this.operationName = operationName;
    	try {
			initialize(context);
		} catch (Exception e) {
			String message = ExceptionUtils.getRootCauseMessage(e);
			throw new RuntimeException(message);
		}
    }

    protected void initialize(String context) throws Exception {
		client = createClient(host, port, serviceId);
		client.initialize();
	}

    protected RMIClient createClient(String hostName, int portNumber, String serviceName) {
    	RMIClient client = new RMIClientImpl(hostName, portNumber, serviceName);
		return client;
	}

	//TODO separate below here to another class

    //TODO add timeout to interface
	public Message invoke(Message request) {
    	//request.addPart("ARIESServiceName", serviceId);
    	//request.addPart("ARIESMethodName", operationName);
    	String correlationId = IdGenerator.createId();
    	
    	try {
    		request.addPart(MessageConstants.PROPERTY_SERVICE_ID, serviceId);
    		request.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
    		
    		String domain = NameUtil.getPackageName(serviceId);
    		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
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

}
