package org.aries.service.jaxws;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.message.Message;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.util.ReflectionUtil;
import org.aries.util.XStreamUtil;


//@HandlerChain(file = "handlers.xml")
@WebService(serviceName="PortalService", endpointInterface = "org.aries.jaxws.JAXWSService")
//@WebContext(contextRoot="/management-recording", urlPattern="/jaxwsSslService")
public class JAXWSServiceImpl implements JAXWSService {

	private static Log log = LogFactory.getLog(JAXWSService.class);
	
	@Resource
	private WebServiceContext webServiceContext;
	
	
    public JAXWSServiceImpl() {
    	//nothing yet
    }

    @PostConstruct
    protected void postConstruct() {
    	log.info("postConstruct");
    	//nothing yet
	}

    @WebMethod(operationName="invoke", action="urn:invoke")
    @WebResult(name = "response")
    public String invoke(@WebParam String requestXML) throws Exception {
    	System.out.println("Received REQUEST:");
    	System.out.println(requestXML);
    	
    	Message request = XStreamUtil.toObject(requestXML);
    	String serviceName = request.getPart("serviceName");
    	
    	//lookup service of specified name, else return error
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
    	Object service = serviceRepository.getServiceInstance(serviceName);
    	if (service == null) {
	    	Message response = new Message();
	    	response.addPart("result", "Service not found: "+serviceName);
	    	String responseXML = XStreamUtil.toXML(response);
	    	return responseXML;
    	}

    	//delegate to service for message processing
    	Message response = processMessage(service, request);
    	String responseXML = XStreamUtil.toXML(response);
    	System.out.println("Sending RESPONSE:");
    	System.out.println(responseXML);
    	return responseXML;
    }

    //TODO move this to shared class
    protected org.aries.message.Message processMessage(Object service, org.aries.message.Message message) throws Exception {
    	String methodName = "process";
    	Class<?>[] parameterTypes = new Class<?>[] { org.aries.message.Message.class };
    	Object[] parameterValues = new Object[] { message };
    	Class<?> resultType = Message.class;
		Method method = ReflectionUtil.findMethod(service.getClass(), methodName, parameterTypes, resultType);
		Object result = ReflectionUtil.invokeMethod(service, method, parameterValues);
		org.aries.message.Message response = (org.aries.message.Message) result;
    	return response;
    }
    
    @PreDestroy
    protected void preDestroy() {
    	log.info("preDestroy");
    	//nothing yet
	}

}
