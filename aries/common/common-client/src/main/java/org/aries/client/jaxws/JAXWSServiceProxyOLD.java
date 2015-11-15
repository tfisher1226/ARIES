package org.aries.client.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Handler;
import org.aries.message.Message;
import org.aries.message.util.MessageUtil;
import org.aries.service.AbstractServiceProxy;
import org.aries.service.ServiceException;
import org.aries.service.ServiceProxy;
import org.aries.util.XStreamUtil;


public class JAXWSServiceProxyOLD extends AbstractServiceProxy implements ServiceProxy {

	private static Log log = LogFactory.getLog(ServiceProxy.class);

	private String host = "localhost";

	private int port;

	private String service;

	private URL url;

	private JAXWSService servicePort;

	
    public JAXWSServiceProxyOLD(String context, String name) throws ServiceException {
    	initialize(context);
    	service = name;
    }
    
    protected void initialize(String context) throws ServiceException {
    	url = createServiceURL(context);
		servicePort = createServicePort(url);
	}

    protected URL createServiceURL(String context) throws ServiceException {
    	String urlString = createServiceURLText(context);
		try {
	    	URL url = new URL(urlString);
	    	return url;
		} catch (MalformedURLException e) {
			throw new ServiceException(e);
		}
    }

    protected String createServiceURLText(String context) throws ServiceException {
    	String urlString = "http://"+host+":"+port+context+"/services";
    	return urlString;
    }

    // Get a handle to the web service's client interface
    protected JAXWSService createServicePort(URL url) {
        WebServiceClient annotation = JAXWSServiceClient.class.getAnnotation(WebServiceClient.class);
		QName qName = new QName(annotation.targetNamespace(), annotation.name());
		JAXWSServiceClient service = new JAXWSServiceClient(url , qName);
        JAXWSService port = service.getPortalServicePort();
        return port;
    }

    
    //separate below here to new class
    
	public Message invoke(Message request) {
		MessageUtil.addPart(request, "serviceName", "org.aries:"+service);
    	try {
	    	String requestXML = XStreamUtil.toXML(request);
	    	String responseXML = servicePort.invoke(requestXML);
	    	Message response = XStreamUtil.toObject(responseXML);
			return response;
		} catch (Throwable e) {
			log.error(e);
			//assuming e is acceptable for propagation (i.e. not like an SQLException etc...)
			throw new ServiceException(e);
		}
	}

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

//    protected Message send(Message request) throws Exception {
//    	request.addPart("serviceName", "org.aries:"+_serviceName);
//    	String requestXML = Transformer.toXML(request);
//    	String responseXML = _servicePort.invoke(requestXML);
//    	Message response = Transformer.toObject(responseXML);
//    	return response;
//    }

}
