package common.tx.service.activation;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.aries.tx.service.AbstractPortTypeFactory;


public class ActivationPortTypeFactory extends AbstractPortTypeFactory {

	//private static final ObjectFactory WSA_OBJECT_FACTORY = new ObjectFactory();
	
    private static ThreadLocal<ActivationService> activationService = new ThreadLocal<ActivationService>();

    private static synchronized ActivationService getActivationService() {
        if (activationService.get() == null)
            activationService.set(new ActivationService());
        return activationService.get();
    }

    public static ActivationPortType getActivationPort() {
        //ActivationPortType port = getActivationService().getPort(ActivationPortType.class, new AddressingFeature(true, true));
        ActivationPortType port = getActivationService().getPort(ActivationPortType.class);
        
//        String messageId = MessageId.getMessageId();
//        String serviceName = CoordinationConstants.ACTIVATION_SERVICE_NAME;
//        String portTypeName = CoordinationConstants.ACTIVATION_ENDPOINT_NAME;
//        String actionName = CoordinationConstants.WSCOOR_ACTION_CREATE_COORDINATION_CONTEXT;
//        String serviceUri = ServiceRegistry.getRegistry().getServiceURI(serviceName)+"/"+portTypeName;
//        applyAddressingHeader(port, serviceUri, actionName, messageId);
        configurePort(port);
        initializePort(port);
        
        /*
        AddressingBuilder builder = AddressingBuilder.getAddressingBuilder();
        AddressingProperties maps = builder.newAddressingProperties();

        Map<String, Object> requestContext = bindingProvider.getRequestContext();
        requestContext.put(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES, maps);
        
        AttributedURIType messageIdUri = WSA_OBJECT_FACTORY.createAttributedURIType();
        messageIdUri.setValue("urn:uuid:" + System.currentTimeMillis());
        maps.setMessageID(messageIdUri);
        
        AttributedURIType actionUri = WSA_OBJECT_FACTORY.createAttributedURIType();
        actionUri.setValue(action);
        maps.setAction(actionUri);
        */

		//requestContext.put(BindingProvider.SOAPACTION_USE_PROPERTY, true);
		//requestContext.put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
        return port;
    }

	public static ActivationPortType getActivationPort(W3CEndpointReference endpointReference) {
        //ActivationPortType port = getActivationService().getPort(endpointReference, ActivationPortType.class, new AddressingFeature(true, true));
        ActivationPortType port = getActivationService().getPort(endpointReference, ActivationPortType.class);
        configurePort(port);
        initializePort(port);
        return port;
    }

//	public static void initializePort(Object port, String action, String messageID) {
//        BindingProvider bindingProvider = (BindingProvider) port;
//        Map<String, Object> requestContext = bindingProvider.getRequestContext();
//    	Handler handler = new JaxWSHeaderContextProcessor();
//        List<Handler> handlers = Collections.singletonList(handler);
//        bindingProvider.getBinding().setHandlerChain(handlers);
//	}
	
	/*
	 * ----
	 * For use with JBossWS
	 * ----
	 * 
	 */

	public static void configurePort(Object port) {
//		AddressingEndpoint endpoint = new AddressingEndpoint(port);
//		endpoint.setServiceName(CoordinationConstants.ACTIVATION_SERVICE_NAME);
//		endpoint.setPortTypeName(CoordinationConstants.ACTIVATION_ENDPOINT_NAME);
//		endpoint.setActionName(CoordinationConstants.WSCOOR_ACTION_CREATE_COORDINATION_CONTEXT);
//		String messageId = MessageId.getMessageId();
//		applyAddressingHeader(endpoint, messageId);
	}


//	public static void initializePort(Object port, String action, String messageID) {
//        BindingProvider bindingProvider = (BindingProvider) port;
//        Map<String, Object> requestContext = bindingProvider.getRequestContext();
//        MAP map = AddressingHelper.outboundMap(requestContext);
//		AddressingHelper.installActionMessageID(map, action, messageID);
//        AddressingHelper.configureRequestContext(requestContext, map.getTo(), action);
//
//    	Handler handler = new JaxWSHeaderContextProcessor();
//        List<Handler> handlers = Collections.singletonList(handler);
//        bindingProvider.getBinding().setHandlerChain(handlers);
//	}

}
