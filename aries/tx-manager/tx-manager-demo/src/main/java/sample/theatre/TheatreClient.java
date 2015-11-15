package sample.theatre;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.TransactionContext;
import common.tx.TransactionManagerFactory;
import common.tx.handler.client.JaxWSHeaderContextProcessor;


public class TheatreClient {

	private static Log log = LogFactory.getLog(TheatreClient.class);
	
	private TheatreService service;
	
	
	public TheatreClient(String host, int port) {
    	try {
    		URL wsdlLocation = new URL(getURI(host, port));
    		service = new TheatreService(wsdlLocation);
    	} catch (Exception e) {
    		log.error("Error", e);
    	}
	}
	
    private String getURI(String host, int port) throws MalformedURLException {
		return "http://"+host+":"+port+"/tx-service/TheatreService/TheatrePortType?wsdl";
	}
    
	
    public void bookSeats(int howMany, int whichArea) {
    	try {
	    	TheatrePortType port = service.getTheatrePortType();
	    	initializePort(port);
	    	port.bookSeats(howMany, whichArea);
    	} catch (Exception e) {
    		log.error("Error", e);
    	}
    }

	public static void initializePort(Object port) {
		TransactionContext transactionContext = TransactionManagerFactory.getTransactionManager().currentTransaction();
		JaxWSHeaderContextProcessor handler = new JaxWSHeaderContextProcessor();
		handler.setTransactionId(transactionContext.getTransactionId());
		
        @SuppressWarnings("rawtypes") List<Handler> handlers = Collections.singletonList((Handler) handler);
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
