package org.aries.tx.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.client.handler.JaxWSHeaderContextProcessor;

import tx.manager.registry.ServiceRegistry;



public abstract class AbstractPortTypeClient {

//	protected abstract String getServiceId();
	
	protected final Log log = LogFactory.getLog(getClass());

    protected URL getWsdlLocation(String serviceId) {
		try {
			String serviceURI = ServiceRegistry.getInstance().getServiceURI(serviceId);
			URL serviceURL = new URL(serviceURI+"?wsdl");
			return serviceURL; 
		} catch (MalformedURLException e) {
    		log.error("Error", e);
    		return null;
		}
    }

	public static void initializePort(Object port) {
		initializePort(port, null, null, null);
	}

	public static void initializePort(Object port, String transactionId) {
		initializePort(port, transactionId, null, null);
	}

	public static void initializePort(Object port, String transactionId, String participantId, String coordinatorId) {
		JaxWSHeaderContextProcessor handler = new JaxWSHeaderContextProcessor();
    	handler.setTransactionId(transactionId);
    	handler.setParticipantId(participantId);
    	handler.setCoordinatorId(coordinatorId);
    	
        List<Handler> handlers = Collections.singletonList((Handler) handler);
    	BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getBinding().setHandlerChain(handlers);
        
		//set timeout
        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put("com.sun.xml.ws.request.timeout", 120000);
		requestContext.put("com.sun.xml.ws.connect.timeout", 120000);
		requestContext.put("com.sun.xml.internal.ws.request.timeout", 120000);
		requestContext.put("com.sun.xml.internal.ws.connect.timeout", 120000);
		requestContext.put("javax.xml.ws.client.connectionTimeout", "120000");
		requestContext.put("javax.xml.ws.client.receiveTimeout", "120000");
		requestContext.put("javax.xml.ws.client.connectionTimeout", "120000");
		requestContext.put("javax.xml.ws.client.receiveTimeout", "120000");
	}
	
}
