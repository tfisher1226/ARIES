package org.aries.tx.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.aries.tx.client.handler.JaxWSHeaderContextProcessor;


public abstract class AbstractPortTypeFactory {

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
	
	/*
	 * ----
	 * For use with JBOssWS
	 * ----
	 * 
	*/

//	public static void applyAddressingHeader(AddressingEndpoint endpoint, String messageId) {
//		applyAddressingHeader(endpoint.getServiceInstance(), endpoint.getServiceName(), endpoint.getPortTypeName(), endpoint.getActionName(), messageId);
//	}
//
//	public static void applyAddressingHeader(Object serviceInstance, String serviceName, String portTypeName, String actionName, String messageId) {
//        String serviceUri = ServiceRegistry.getRegistry().getServiceURI(serviceName)+"/"+portTypeName;
//        applyAddressingHeader(serviceInstance, serviceUri, actionName, messageId);
//	}
//
//    public static void applyAddressingHeader(Object port, String serviceUri, String actionName, String messageId) {
//        BindingProvider bindingProvider = (BindingProvider) port;
//        Map<String, Object> requestContext = bindingProvider.getRequestContext();
//        MAP map = AddressingHelper.createRequestContext(serviceUri, messageId);
//        AddressingHelper.configureRequestContext(requestContext, map, serviceUri, actionName);
//	}
}
