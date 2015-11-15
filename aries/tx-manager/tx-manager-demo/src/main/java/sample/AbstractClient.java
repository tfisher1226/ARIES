package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.SystemException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import common.tx.TransactionContext;
import common.tx.TransactionManagerFactory;
import common.tx.bridge.outbound.JaxWSTxOutboundBridgeHandler;
import common.tx.handler.client.JaxWSHeaderContextProcessor;


public abstract class AbstractClient {

	protected void initializePort(Object port) throws SystemException {
		TransactionContext transactionContext = TransactionManagerFactory.getTransactionManager().currentTransaction();
        @SuppressWarnings("rawtypes") List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(new JaxWSHeaderContextProcessor(transactionContext.getTransactionId()));
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

	protected void initializePortWithBridge(Object port) throws SystemException {
		TransactionContext transactionContext = TransactionManagerFactory.getTransactionManager().currentTransaction();
        @SuppressWarnings("rawtypes") List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(new JaxWSTxOutboundBridgeHandler());
        handlers.add(new JaxWSHeaderContextProcessor(transactionContext.getTransactionId()));
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
