package org.aries.client.jaxws;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.Handler;
import org.aries.message.Message;
import org.aries.message.util.MessageUtil;
import org.aries.service.AbstractServiceProxy;
import org.aries.service.ServiceProxy;
import org.aries.util.ObjectUtil;
import org.aries.util.ReflectionUtil;


public class JAXWSServiceProxy extends AbstractServiceProxy implements ServiceProxy {

	private static Log log = LogFactory.getLog(JAXWSServiceProxy.class);
	
	private String serviceURL;
	
	//private QName serviceQName;
	
	//private QName portQName;

	//private Class<?> interfaceClass;

	private String methodName;

	private String resultName;

	//private URL wsdlUrl;

	private JAXWSPortFactory portFactory;

	private JAXWSHeaderHandlerResolver handlerResolver;

	private Object port;

	
	public JAXWSServiceProxy(String serviceURL, QName serviceQName, QName portQName, Class<?> interfaceClass, String methodName, String resultName) {
		this.serviceURL = serviceURL;
		//this.serviceQName = serviceQName;
		//this.portQName = portQName;
		//this.interfaceClass = interfaceClass;
		this.methodName = methodName;
		this.resultName = resultName;
		//TODO add validatation
		try {
			URL wsdlUrl = new URL(serviceURL+"?wsdl");
			this.portFactory = new JAXWSPortFactory(wsdlUrl, serviceQName, portQName, interfaceClass);
			//TODO portFactory.initialize(userName, password, systemId, correlationId);
			//TODO this.port = createPort();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}


	protected Object createPort() {
		//First initialize the header handler with security credentials
		initializeHeaderHandler(userName, password, systemId);

		//Now create the PORT here
		this.port = portFactory.getPort();

		//ServletContext servletContext = ServletLifecycle.getCurrentServletContext();
		BindingProvider bindingProvider = (BindingProvider) port;
		//String baseURL = "http://localhost:8080/xtsdemowebservices/";
		//address a string representation of the service URL. null is ok if this is a service located in the
		//String address = baseURL + "RestaurantServiceService";
		//String string = wsdlLocation.getAuthority().toString();
		//String address = string.substring(0, string.length()-5);
		//String address = "http://localhost:8080/nightout-restaurant-service/RestaurantService";

		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		Map<String, Object> responseContext = bindingProvider.getResponseContext();

		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
		requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

		if (correlationId != null) {
			requestContext.put("correlationId", correlationId);
			responseContext.put("correlationId", correlationId);
		}

		//set timeout
		//requestContext.put("com.sun.xml.ws.request.timeout", 120000);
		//requestContext.put("com.sun.xml.ws.connect.timeout", 120000);
		//requestContext.put("com.sun.xml.internal.ws.request.timeout", 120000);
		//requestContext.put("com.sun.xml.internal.ws.connect.timeout", 120000);
		requestContext.put("javax.xml.ws.client.connectionTimeout", "120000");
		requestContext.put("javax.xml.ws.client.receiveTimeout", "120000");
		
		//requestContext.put(BindingProviderProperties.REQUEST_TIMEOUT, 3000);
		//requestContext.put(BindingProviderProperties.CONNECT_TIMEOUT, 1000);
		
		/* TODO Taken out only for now */
		//configureClientHandler(bindingProvider);
		configureHeaderHandler(bindingProvider, userName, password, systemId);
		return port;
	}

	public void initializeHeaderHandler(String userName, String password, String systemId) {
		handlerResolver = new JAXWSHeaderHandlerResolver(userName, password, systemId);
		portFactory.setHandlerResolver(handlerResolver);
	}

	public void configureHeaderHandler(BindingProvider bindingProvider, String userName, String password, String systemId) {
		//List<javax.xml.ws.handler.Handler> handlers = new ArrayList<javax.xml.ws.handler.Handler>(1);
		//handlers.add(createClientHandler("org.aries.platform.tx.bridge.outbound.JaxWSTxOutboundBridgeHandler"));
		//handlers.add(createClientHandler("org.aries.platform.tx.handler.client.JaxWSHeaderContextProcessor"));
		//handlers.add(new WSSEHeaderHandler());
		//bindingProvider.getBinding().setHandlerChain(handlers);
		//bindingProvider.getBinding().getHandlerChain().add(new WSSEHeaderHandler());
	}

	public void propagateCorrelationIdToHeaderHandler(Object correlationId) {
		handlerResolver.setCorrelationId(correlationId);
	}

	@SuppressWarnings("rawtypes") 
	protected void configureClientHandler(BindingProvider bindingProvider) {
		List<javax.xml.ws.handler.Handler> handlers = new ArrayList<javax.xml.ws.handler.Handler>(1);
		//handlers.add(createClientHandler("org.aries.platform.tx.bridge.outbound.JaxWSTxOutboundBridgeHandler"));
		handlers.add(createClientHandler("org.aries.platform.tx.handler.client.JaxWSHeaderContextProcessor"));
		bindingProvider.getBinding().setHandlerChain(handlers);
	}

	protected javax.xml.ws.handler.Handler<?> createClientHandler(String className) {
		javax.xml.ws.handler.Handler<?> instance = ObjectUtil.newInstance(className);
		return instance;
	}
	
//    /**
//     * @param config The servlet config
//     * @param property The property name
//     * @param defaultValue The default value.
//     * @return The initialization property value or the default value if not present. 
//     */
//	protected String getURL(ServletConfig servletContext, String property, String defaultValue) {
//        // allow command line override via system property
//        String value = System.getProperty(property);
//        if (value == null) {
//            value = servletContext.getInitParameter(property) ;
//        }
//        return (value == null ? defaultValue : value) ;
//    }
    

	@Override
	public Message invoke(Message request) {
		if (port == null)
			port = createPort();

		Object correlationId = request.getCorrelationId();
		if (correlationId != null) {
			propagateCorrelationIdToHeaderHandler(correlationId);
			((BindingProvider) port).getRequestContext().put("correlationId", correlationId);
			((BindingProvider) port).getResponseContext().put("correlationId", correlationId);
		}
		
		//Service service = Service.create(wsdlLocation, serviceName);
		//Dispatch dispatch = service.createDispatch(portName, StreamSource.class, Mode.PAYLOAD);
		
		//Set timeout until a connection is established
		//((BindingProvider)port).getRequestContext().put("javax.xml.ws.client.connectionTimeout", "6000");

		//Set timeout until the response is received
		//((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.receiveTimeout", "1000");

		Map<String, Object> parts = MessageUtil.getPartMap(request);
		int parameterCount = parts.size();
		if (methodName.equals("process")) {
			parameterCount = 1;
		}
		
		Class<?>[] parameterTypes = new Class<?>[parameterCount];
		Object[] parameterValues = new Object[parameterCount];
		Class<?> returnType = void.class;

		if (methodName.equals("process")) {
			returnType = Message.class;
			parameterTypes[0] = Message.class;
			parameterValues[0] = request;  
		} else {
			if (!StringUtils.isEmpty(resultName))
				returnType = Object.class;
			Iterator<String> iterator = parts.keySet().iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String key = iterator.next();
				Object value = parts.get(key);
				if (value != null) {
					parameterTypes[i] = value.getClass();
					parameterValues[i] = value;
				}
			}
		}
		
		//Method method = ReflectionUtil.findMethod(port.getClass(), methodName, parameterTypes, returnType);
		Method method = ReflectionUtil.findMethodByName(port.getClass(), methodName);
		Assert.notNull(method, "Method not found: "+methodName);
		Assert.notNull(method.getReturnType(), "Result-type not specified");
		
		try {
			logInvokeServiceStarted(serviceURL, methodName);
			
			//The invocation call occurs here
			Object resultValue = method.invoke(port, parameterValues);

			logInvokeServiceComplete(serviceURL, methodName);

			Message response = new Message();
			MessageUtil.addPart(response, resultName, resultValue);
			return response;
		} catch (Exception e) {
			logInvokeServiceAborted(serviceURL, methodName, ExceptionUtils.getRootCauseMessage(e));
			//String message = ExceptionUtils.getRootCauseMessage(e);
			//TODO Throw a meaninful exception from here
			throw new RuntimeException(e);
		}
	}

	@Override
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

	
	protected void logInvokeServiceStarted(String serviceURL, String operation) {
		//EventLog.getInstance().trace("Invoke Service started: "+serviceURL+"/"+operation);
		log.info("Invoke Service started: "+serviceURL+"/"+operation);
	}

	protected void logInvokeServiceComplete(String serviceURL, String operation) {
		//EventLog.getInstance().trace("Invoke Service complete: "+serviceURL+"/"+operation);
		log.info("Invoke Service complete: "+serviceURL+"/"+operation);
	}

	protected void logInvokeServiceAborted(String serviceURL, String operation, String exception) {
		//EventLog.getInstance().trace("Invoke Service aborted: "+serviceURL+"/"+methodName);
		log.info("Invoke Service aborted: "+serviceURL+"/"+methodName);
	}

}
