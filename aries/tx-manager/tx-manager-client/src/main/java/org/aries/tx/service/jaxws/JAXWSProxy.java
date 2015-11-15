package org.aries.tx.service.jaxws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.aries.client.AbstractEndpoint;
import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionManagerFactory;
import org.aries.tx.client.handler.JaxWSHeaderContextProcessor;

import common.tx.client.TEMPJaxWSTxOutboundBridgeHandler;



public class JAXWSProxy extends AbstractEndpoint {

	protected String host;

	protected int port;

	protected String destinationURI;
	
	
	public JAXWSProxy() {
		//nothing for now
	}
	
	public JAXWSProxy(String destinationURI) {
		this.destinationURI = destinationURI;
	}

	public JAXWSProxy(String host, int port) {
		this.host = host;
		this.port = port;
	}

	//@Override
	public String getServiceId() {
		return null;
	}

	//@Override
	public String getServiceUrl() {
		return destinationURI;
	}
	
	@Override
	public void initialize() throws Exception {
		//nothing for now
	}
	
	@Override
	public void reset() throws Exception {
		//nothing for now
	}

	@Override
	public void close() throws Exception {
		//nothing for now
	}
	
	public void initializePort(Object port) {
        @SuppressWarnings("rawtypes") List<Handler> handlers = new ArrayList<Handler>();
        JaxWSHeaderContextProcessor headerHandler = new JaxWSHeaderContextProcessor();
        headerHandler.setTransactionId(getTransactionId());
        headerHandler.setCorrelationId(getCorrelationId());
		handlers.add(headerHandler);
		
		if (port instanceof BindingProvider) {
	        BindingProvider bindingProvider = (BindingProvider) port;
	        bindingProvider.getBinding().setHandlerChain(handlers);
	
			//set timeout
	        Map<String, Object> requestContext = bindingProvider.getRequestContext();
	        requestContext.put("com.sun.xml.ws.request.timeout", 1200000);
			requestContext.put("com.sun.xml.ws.connect.timeout", 1200000);
			requestContext.put("com.sun.xml.internal.ws.request.timeout", 1200000);
			requestContext.put("com.sun.xml.internal.ws.connect.timeout", 1200000);
			requestContext.put("javax.xml.ws.client.connectionTimeout", "1200000");
			requestContext.put("javax.xml.ws.client.receiveTimeout", "1200000");
			requestContext.put("javax.xml.ws.client.connectionTimeout", "1200000");
			requestContext.put("javax.xml.ws.client.receiveTimeout", "1200000");
		}
	}

	public void initializePortWithBridge(Object port) {
		TransactionContext transactionContext = TransactionManagerFactory.getTransactionManager().currentTransaction();
        @SuppressWarnings("rawtypes") List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(new TEMPJaxWSTxOutboundBridgeHandler());
        handlers.add(new JaxWSHeaderContextProcessor(transactionContext.getTransactionId()));

		if (port instanceof BindingProvider) {
	        BindingProvider bindingProvider = (BindingProvider) port;
	        bindingProvider.getBinding().setHandlerChain(handlers);
	
			//set timeout
	        Map<String, Object> requestContext = bindingProvider.getRequestContext();
	        requestContext.put("com.sun.xml.ws.request.timeout", 1200000);
			requestContext.put("com.sun.xml.ws.connect.timeout", 1200000);
			requestContext.put("com.sun.xml.internal.ws.request.timeout", 1200000);
			requestContext.put("com.sun.xml.internal.ws.connect.timeout", 1200000);
			requestContext.put("javax.xml.ws.client.connectionTimeout", "1200000");
			requestContext.put("javax.xml.ws.client.receiveTimeout", "1200000");
			requestContext.put("javax.xml.ws.client.connectionTimeout", "1200000");
			requestContext.put("javax.xml.ws.client.receiveTimeout", "1200000");
		}
	}

	//@Override
	public void send(Serializable request) throws Exception {
		// TODO Auto-generated method stub
	}
	
	//@Override
	public <T extends Serializable> T invoke(Serializable request) throws Exception {
		return null;
	}

	private Map<String, org.aries.Handler<?>> handlers;
	
	public <T extends Serializable> void addResponseHandler(String messageClass, org.aries.Handler<T> handler) {
	}
	
}
